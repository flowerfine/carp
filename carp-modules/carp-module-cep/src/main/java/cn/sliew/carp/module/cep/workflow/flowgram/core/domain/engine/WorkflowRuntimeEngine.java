package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.engine;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IPort;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine.EngineServices;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine.IEngine;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.IExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.SnapshotData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.ITask;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.TaskParams;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.container.WorkflowRuntimeContainer;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.context.WorkflowRuntimeContext;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.task.WorkflowRuntimeTask;
import cn.sliew.carp.module.cep.workflow.flowgram.util.NodeGroupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class WorkflowRuntimeEngine extends IEngine {

    private IValidation validation;
    private IExecutor executor;

    public WorkflowRuntimeEngine(EngineServices services) {
        this.validation = services.getValidation();
        this.executor = services.getExecutor();
    }

    @Override
    public ITask invoke(IWorkflowSchema schema, WorkflowInputs inputs) {
        WorkflowRuntimeContext context = WorkflowRuntimeContext.create();
        context.init(schema, inputs);
        boolean validate = validate(schema, inputs, context);
        if (!validate) {
            CompletableFuture<WorkflowOutputs> completableFuture = new CompletableFuture<>();
            completableFuture.complete(null);
            return WorkflowRuntimeTask.create(new TaskParams()
                    .setProcessing(completableFuture)
                    .setContext(context)
            );
        }
        CompletableFuture<WorkflowOutputs> future = process(context);
        future.thenAccept(outputs -> {
            context.dispose();
        });
        return WorkflowRuntimeTask.create(new TaskParams()
                .setProcessing(future)
                .setContext(context)
        );
    }

    @Override
    public void executeNode(IContext context, INode node) {
        if (!canExecuteNode(context, node)) {
            return;
        }

        context.getStatusCenter().nodeStatus(node.getId()).process();
        ISnapshot snapshot = context.getSnapshotCenter().create(new SnapshotData()
                .setNodeID(node.getId())
                .setData(node.getData())
        );

        List<INode> nextNodes = new ArrayList<>();
        try {
            WorkflowInputs nodeInputs = context.getState().getNodeInputs(node);
            snapshot.update(new SnapshotData().setInputs(nodeInputs));
            CompletableFuture<INodeExecutor.ExecutionResult> future = executor.execute(new INodeExecutor.ExecutionContext()
                    .setNode(node)
                    .setInputs(nodeInputs)
                    .setRuntime(context)
                    .setContainer(WorkflowRuntimeContainer.instance())
                    .setSnapshot(snapshot)
            );
            INodeExecutor.ExecutionResult executionResult = future.join();
            if (Objects.nonNull(context.getStatusCenter().getWorkflow().getTerminated())
                    && context.getStatusCenter().getWorkflow().getTerminated()) {
                return;
            }
            snapshot.update(new SnapshotData()
                    .setOutputs(executionResult.getOutputs())
                    .setBranch(executionResult.getBranch())
            );

            context.getState().setNodeOutputs(node, executionResult.getOutputs());
            context.getState().addExecutedNode(node);
            context.getStatusCenter().nodeStatus(node.getId()).success();
            nextNodes = getNextNodes(context, node, executionResult.getBranch());
        } catch (Exception e) {
            snapshot.update(new SnapshotData().setError(e.getMessage()));

            context.getMessageCenter().error(new IMessageCenter.MessageData()
                    .setNodeID(node.getId())
                    .setMessage(e.getMessage())
            );
            context.getStatusCenter().nodeStatus(node.getId()).fail();
            log.error(e.getMessage(), e);
            throw e;
        }
        executeNext(context, node, nextNodes);
    }

    private CompletableFuture<WorkflowOutputs> process(IContext context) {
        CompletableFuture<WorkflowOutputs> future = new CompletableFuture<>();
        INode startNode = context.getDocument().getStart();
        context.getStatusCenter().getWorkflow().process();
        try {
            executeNode(context, startNode);
            context.getStatusCenter().getWorkflow().success();
            future.complete(context.getIoCenter().getOutputs());
        } catch (Exception e) {
            context.getStatusCenter().getWorkflow().fail();
            future.completeExceptionally(e);
        }
        return future;
    }

    private boolean validate(IWorkflowSchema schema, WorkflowInputs inputs, IContext context) {
        IValidation.ValidationResult validationResult = validation.invoke(schema, inputs);
        if (validationResult.isValid()) {
            return true;
        }

        validationResult.getErrors().forEach(error -> {
            context.getMessageCenter().error(new IMessageCenter.MessageData().setMessage(error));
        });
        context.getStatusCenter().getWorkflow().fail();
        return false;
    }


    private boolean canExecuteNode(IContext context, INode node) {
        List<INode> prev = node.getPrev();
        if (CollectionUtils.isEmpty(prev)) {
            return true;
        }

        for (INode prevNode : prev) {
            if (!context.getState().isExecutedNode(prevNode)) {
                return false;
            }
        }
        return true;
    }

    private List<INode> getNextNodes(IContext context, INode node, String branch) {
        List<INode> allNextNodes = node.getNext();
        if (StringUtils.isBlank(branch)) {
            return allNextNodes;
        }

        Optional<IPort> targetPort = node.getPorts().getOutputs().stream().filter(port -> StringUtils.equals(port.getId(), branch)).findAny();
        if (targetPort.isEmpty()) {
            throw new RuntimeException("Branch " + branch + " not found");
        }

        Set<String> nextNodeIDs = targetPort.get().getEdges().stream().map(edge -> edge.getTo().getId()).collect(Collectors.toSet());

        List<INode> nextNodes = allNextNodes.stream().filter(nextNode -> nextNodeIDs.contains(nextNode.getId())).collect(Collectors.toList());
        List<INode> skipNodes = allNextNodes.stream().filter(nextNode -> !nextNodeIDs.contains(nextNode.getId())).collect(Collectors.toList());

        List<List<INode>> nextGroups = nextNodes.stream().map(nextNode -> {
            List<INode> list = new ArrayList<>();
            list.add(nextNode);
            list.addAll(nextNode.getSuccessors());
            return list;
        }).collect(Collectors.toList());
        List<List<INode>> skipGroups = skipNodes.stream().map(skipNode -> {
            List<INode> list = new ArrayList<>();
            list.add(skipNode);
            list.addAll(skipNode.getSuccessors());
            return list;
        }).collect(Collectors.toList());

        NodeGroupUtil.NodeComparisonResult nodeComparisonResult = NodeGroupUtil.compareNodeGroups(nextGroups, skipGroups);
        nodeComparisonResult.getUniqueToB().forEach(uniqueB -> {
            context.getState().addExecutedNode(uniqueB);
        });

        return nextNodes;
    }

    private void executeNext(IContext context, INode node, List<INode> nextNodes) {
        switch (node.getType()) {
            case END:
            case BLOCK_END:
            case BREAK:
            case CONTINUE:
                return;
            default:
        }

        if (CollectionUtils.isEmpty(nextNodes)) {
            throw new RuntimeException("Node " + node.getId() + " has no next nodes");
        }

        nextNodes.stream().forEach(nextNode -> executeNode(context, nextNode));
    }
}

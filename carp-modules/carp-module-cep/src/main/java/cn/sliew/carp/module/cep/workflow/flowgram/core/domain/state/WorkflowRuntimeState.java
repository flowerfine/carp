package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.state;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state.IState;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.*;
import cn.sliew.carp.module.cep.workflow.flowgram.util.WorkflowRuntimeType;
import cn.sliew.milky.common.util.JacksonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkflowRuntimeState extends IState {

    private static final String REGEX = "\\{\\{([^\\}]+)\\}\\}";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private Set<String> executedNodes;

    public WorkflowRuntimeState() {
        setId(UUIDUtil.randomUUId());
    }

    @Override
    public void init(IWorkflowSchema schema) {
        if (Objects.nonNull(schema)) {
            setGlobalVariable(schema.getGlobalVariable());
        }
        this.executedNodes = new HashSet<>();
    }

    @Override
    public void dispose() {
        this.executedNodes.clear();
    }

    @Override
    public WorkflowInputs getNodeInputs(INode node) {
        IJsonSchema inputs = node.getDeclare().getInputs();
        Map<String, IValue> inputsValues = node.getDeclare().getInputsValues();
        return parseInputs(inputsValues, inputs);
    }

    @Override
    public void setNodeOutputs(INode node, WorkflowOutputs outputs) {
        IJsonSchema outputsDeclare = node.getDeclare().getOutputs();
        if (Objects.isNull(outputsDeclare)
                || !StringUtils.equals(outputsDeclare.getType(), WorkflowVariableType.OBJECT.getValue())
                || MapUtils.isEmpty(outputsDeclare.getProperties())) {
            return;
        }

        outputsDeclare.getProperties().forEach((key, typeInfo) -> {
            if (Objects.isNull(key) || Objects.isNull(typeInfo)) {
                return;
            }
            WorkflowVariableType type = WorkflowVariableType.of(typeInfo.getType());
            WorkflowVariableType itemsType = null;
            if (Objects.nonNull(typeInfo.getItems())) {
                itemsType = WorkflowVariableType.of(typeInfo.getItems().getType());
            }
            Object value = outputs.get(key);
            if (Objects.isNull(value)) {
                value = parseJSONContent(typeInfo.getDefaultValue(), type);
            }

            IVariableStore.SetVariableParam setVariableParam = new IVariableStore.SetVariableParam()
                    .setNodeID(node.getId())
                    .setKey(key)
                    .setValue(value);
            setVariableParam.setType(type);
            setVariableParam.setItemsType(itemsType);
            getVariableStore().setVariable(setVariableParam);
        });
    }

    @Override
    public WorkflowInputs parseInputs(Map<String, IValue> values, IJsonSchema declare) {
        if (Objects.isNull(declare) || MapUtils.isEmpty(values)) {
            return new WorkflowInputs();
        }

        WorkflowInputs inputs = new WorkflowInputs();
        for (Map.Entry<String, IValue> entry : values.entrySet()) {
            String key = entry.getKey();
            IValue flowValue = entry.getValue();
            if (Objects.isNull(declare.getProperties()) || !declare.getProperties().containsKey(key)) {
                continue;
            }
            IJsonSchema typeInfo = declare.getProperties().get(key);
            WorkflowVariableType declareType = WorkflowVariableType.of(typeInfo.getType());
            IVariableParseResult result = parseFlowValue(flowValue, declareType);
            if (Objects.isNull(result)) {
                continue;
            }
            if (!Objects.equals(declareType, result.getType())) {
                continue;
            }
            inputs.put(key, result.getValue());
        }

        return inputs;
    }

    @Override
    public IVariableParseResult parseRef(IFlowRefValue ref) {
        if (Objects.isNull(ref.getContent())) {
            throw new RuntimeException("IFlowRefValue lack content");
        }
        List<String> content = (List<String>) ref.getContent();
        if (CollectionUtils.isEmpty(content) && CollectionUtils.size(content) < 2) {
            return null;
        }

        String nodeId = content.get(0);
        String variableKey = content.get(1);
        String[] variablePath = new String[0];
        if (content.size() > 2) {
            variablePath = content.subList(2, content.size()).toArray(new String[0]);
        }
        return getVariableStore().getValue(nodeId, variableKey, variablePath).orElse(null);
    }

    @Override
    public IVariableParseResult<String> parseTemplate(IFlowTemplateValue template) {
        if (Objects.isNull(template.getContent())) {
            throw new RuntimeException("IFlowTemplateValue lack content");
        }
        String content = (String) template.getContent();
        if (StringUtils.isBlank(content)) {
            return null;
        }

        Pattern.compile("/\\{\\{([^\\}]+)\\}\\}/g");
        Matcher matcher = PATTERN.matcher(content);

        String parsedValue = matcher.replaceAll(matchResult -> {
            String fullMatch = matchResult.group(0);
            String trimed = matchResult.group(1).trim();
            DefaultFlowRefValue refValue = new DefaultFlowRefValue()
                    .setType("ref")
                    .setContent(Arrays.asList(StringUtils.split(trimed, ".")));

            IVariableParseResult refParseResult = parseRef(refValue);
            if (Objects.isNull(refParseResult)) {
                return "";
            }
            return String.valueOf(refParseResult.getValue());
        });

        IVariableParseResult parseResult = new IVariableParseResult();
        parseResult.setType(WorkflowVariableType.STRING);
        parseResult.setValue(parsedValue);
        return parseResult;
    }

    @Override
    public IVariableParseResult parseFlowValue(IValue flowValue, WorkflowVariableType declareType) {
        if (StringUtils.isBlank(flowValue.getType())) {
            throw new RuntimeException("Invalid flow value type: " + flowValue.getType());
        }
        if (StringUtils.equals(flowValue.getType(), "constant")) {
            Object value = parseJSONContent(flowValue.getContent(), declareType);
            WorkflowVariableType type = declareType;
            if (Objects.isNull(type)) {
                type = WorkflowRuntimeType.getWorkflowType(value);
            }
            if (Objects.isNull(value) || Objects.isNull(type)) {
                return null;
            }
            IVariableParseResult parseResult = new IVariableParseResult();
            parseResult.setValue(value);
            parseResult.setType(type);
            return parseResult;
        } else if (StringUtils.equals(flowValue.getType(), "ref")) {
            return parseRef((IFlowRefValue) flowValue);
        } else if (StringUtils.equals(flowValue.getType(), "expression")) {
            throw new UnsupportedOperationException("Unsupport flow value type: " + flowValue.getType());
        } else if (StringUtils.equals(flowValue.getType(), "template")) {
            return parseTemplate((IFlowTemplateValue) flowValue);
        } else {
            throw new RuntimeException("Unknown flow value type: " + flowValue.getType());
        }
    }

    @Override
    public boolean isExecutedNode(INode node) {
        return executedNodes.contains(node.getId());
    }

    @Override
    public void addExecutedNode(INode node) {
        executedNodes.add(node.getId());
    }

    private Object parseJSONContent(Object jsonContent, WorkflowVariableType declareType) {
        if (Objects.nonNull(declareType)
                && (declareType == WorkflowVariableType.OBJECT
                || declareType == WorkflowVariableType.ARRAY
                || declareType == WorkflowVariableType.MAP)
                && jsonContent instanceof String) {
            return JacksonUtil.toJsonNode(jsonContent);
        }
        return jsonContent;
    }

    private void setGlobalVariable(IJsonSchema globalVariableDeclare) {
        if (Objects.isNull(globalVariableDeclare)) {
            return;
        }

        if (!StringUtils.equals(globalVariableDeclare.getType(), JsonSchemaBasicType.OBJECT.getValue())
                && MapUtils.isEmpty(globalVariableDeclare.getProperties())) {
            return;
        }

        globalVariableDeclare.getProperties().forEach((key, typeInfo) -> {
            if (Objects.isNull(key) || Objects.isNull(typeInfo)) {
                return;
            }
            getVariableStore().setVariable((IVariableStore.SetVariableParam) new IVariableStore.SetVariableParam()
                    .setNodeID("global")
                    .setKey(key)
                    .setValue(parseJSONContent(typeInfo.getDefaultValue(), WorkflowVariableType.of(typeInfo.getType())))
                    .setType(WorkflowVariableType.of(typeInfo.getType()))
                    .setItemsType(WorkflowVariableType.of(typeInfo.getItems().getType()))
            );
        });
    }
}

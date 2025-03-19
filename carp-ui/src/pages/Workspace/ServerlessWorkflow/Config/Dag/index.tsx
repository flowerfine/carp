import React from "react";
import {useLocation} from "@umijs/max";
import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import X6Menubar from "@/components/X6/Menubar";
import X6Toolbar from "@/components/X6/Toolbar";
import Dnd from "@/pages/Workspace/ServerlessWorkflow/Instance/dnd";
import {Connect} from "@/pages/Workspace/ServerlessWorkflow/Instance/connect";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Instance/init-shade";
import {SERVERLESS_WORKFLOW_EDGE} from "@/components/X6/Shape/ServerlessWorkflowNode";
import NodeConfig from "@/pages/Workspace/ServerlessWorkflow/Instance/NodeConfig";
import {ServerlessWorkflowService} from "@/services/workspace/workflow/serverless-workflow.service";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowService} from "@/services/workspace/workflow/workflow.service";

const Page: React.FC = () => {
  const workflowDefinition = useLocation().state as WorkspaceWorkflowAPI.WorkflowDefinition;

  return (
    <XFlow>
      <X6Layout
        menubar={<X6Menubar
          name={workflowDefinition.name}
          onNameChange={(name) => {
            WorkflowService.updateName({id: workflowDefinition.id, name: name})
          }}
          onSave={(data, graph) => {
            console.log('X6Menubar onSave', data, graph);
          }}
          onExecute={(data, graph) => {
            const param = {
              petId: 10
            }
            ServerlessWorkflowService.execute(param, graph).then(response => {
              console.log('X6Menubar onExecute', data, graph, response);
            })
          }}
        />}
        toolbar={<X6Toolbar/>}
        dnd={<Dnd/>}
        body={(
          <>
            <XFlowGraph
              centerView
              zoomable
              zoomOptions={{
                minScale: 0.5,
                maxScale: 1.5,
              }}
              pannable
              fitView
              connectionOptions={{
                snap: true,
                allowBlank: false,
                allowLoop: false,
                highlight: true,
                connectionPoint: 'anchor',
                anchor: 'center',
                connector: 'smooth',
              }}
              connectionEdgeOptions={{
                shape: SERVERLESS_WORKFLOW_EDGE,
                animated: true,
                zIndex: -1,
              }}
            />
          </>
        )}
      />
      <InitNode data={workflowDefinition}/>
      <Connect/>
      <NodeConfig/>
    </XFlow>
  );
};

export default Page;

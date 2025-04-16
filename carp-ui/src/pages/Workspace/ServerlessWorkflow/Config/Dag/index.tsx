import React from "react";
import {history, useLocation} from "@umijs/max";
import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import X6Menubar from "@/components/X6/Menubar";
import X6Toolbar from "@/components/X6/Toolbar";
import Dnd from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/dnd";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/init-node";
import {SERVERLESS_WORKFLOW_EDGE} from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/shape";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {ServerlessWorkflowInstanceService} from "@/services/workspace/workflow/serverless-workflow-instance.service";
import {
  ServerlessWorkflowDefinitionService
} from "@/services/workspace/workflow/serverless-workflow-definition.service";

const Page: React.FC = () => {
  const workflowDefinition = useLocation().state as WorkspaceWorkflowAPI.ServerlessWorkflowDefinition;

  return (
    <XFlow>
      <X6Layout
        menubar={<X6Menubar
          name={workflowDefinition.name}
          onNameChange={(name) => {
            ServerlessWorkflowDefinitionService.updateName({id: workflowDefinition.id, name: name})
          }}
          onSave={(data, graph) => {
            console.log('X6Menubar onSave', data, graph);
          }}
          onExecute={(data, graph) => {
            ServerlessWorkflowInstanceService.run({id: workflowDefinition.id}).then(response => {
              if (response.success && response.data) {
                ServerlessWorkflowInstanceService.get(response.data).then(dataResp => {
                  if (dataResp.success && dataResp.data) {
                    history.push('/workspace/serverless-workflow/instance/detail', dataResp.data);
                  }
                })
              }
            })
          }}
        />}
        toolbar={<X6Toolbar/>}
        dnd={<Dnd/>}
        body={(
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
        )}
      />
      <InitNode data={workflowDefinition}/>
    </XFlow>
  );
};

export default Page;

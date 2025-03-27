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
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import {ModalFormProps} from "@/typings";
import {WorkflowInstanceService} from "@/services/workspace/workflow/workflow-instance.service";

const Page: React.FC<ModalFormProps<WorkspaceWorkflowAPI.WorkflowDefinition>> = ({data}) => {
  const workflowDefinition = useLocation().state as WorkspaceWorkflowAPI.WorkflowDefinition;

  return (
    <XFlow>
      <X6Layout
        menubar={<X6Menubar
          name={workflowDefinition.name}
          onNameChange={(name) => {
            WorkflowDefinitionService.updateName({id: workflowDefinition.id, name: name})
          }}
          onSave={(data, graph) => {
            console.log('X6Menubar onSave', data, graph);
          }}
          onExecute={(data, graph) => {
            WorkflowInstanceService.run({id: workflowDefinition.id}).then(response => {
              if (response.success && response.data) {
                WorkflowInstanceService.get(response.data).then(dataResp => {
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

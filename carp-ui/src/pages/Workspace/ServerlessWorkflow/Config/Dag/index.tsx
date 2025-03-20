import React from "react";
import {useLocation} from "@umijs/max";
import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import X6Menubar from "@/components/X6/Menubar";
import X6Toolbar from "@/components/X6/Toolbar";
import Dnd from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/dnd";
import {Connect} from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/connect";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/init-node";
import {SERVERLESS_WORKFLOW_EDGE} from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/shape";
import NodeConfig from "@/pages/Workspace/ServerlessWorkflow/Config/Dag/NodeConfig";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import {ModalFormProps} from "@/typings";

const Page: React.FC<ModalFormProps<WorkspaceWorkflowAPI.WorkflowDefinition>> = ({data})  => {
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

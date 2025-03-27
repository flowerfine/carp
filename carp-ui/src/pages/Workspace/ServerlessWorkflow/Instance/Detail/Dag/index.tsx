import React from "react";
import {XFlow, XFlowGraph} from "@antv/xflow";
import X6Layout from "@/components/X6/Layout";
import {useLocation} from "@umijs/max";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Instance/Detail/Dag/init-node";

const WorkspaceServerlessWorkflowInstanceDetailDagWeb: React.FC = () => {
  const workflowInstance = useLocation().state as WorkspaceWorkflowAPI.WorkflowInstance;

  console.log('WorkspaceServerlessWorkflowInstanceDetailDagWeb', workflowInstance)

  return (
    <XFlow>
      <X6Layout
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
          />
        )}
      />
      <InitNode data={workflowInstance}/>
    </XFlow>
  )
}

export default WorkspaceServerlessWorkflowInstanceDetailDagWeb;

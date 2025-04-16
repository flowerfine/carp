import React from "react";
import {XFlow, XFlowGraph} from "@antv/xflow";
import X6Layout from "@/components/X6/Layout";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Instance/Detail/Dag/init-node";
import {CICD_NODE} from "@/pages/Workspace/ServerlessWorkflow/Instance/Detail/Dag/shape";
import {Props} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";

const WorkspaceServerlessWorkflowInstanceDetailDagWeb: React.FC<Props<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>> = ({data}) => {

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
            connectionEdgeOptions={{
              shape: CICD_NODE,
              animated: true,
              zIndex: -1,
            }}
          />
        )}
      />
      <InitNode data={data}/>
    </XFlow>
  )
}

export default WorkspaceServerlessWorkflowInstanceDetailDagWeb;

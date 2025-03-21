import React from "react";
import {XFlow, XFlowGraph} from "@antv/xflow";
import X6Layout from "@/components/X6/Layout";

const WorkspaceServerlessWorkflowInstanceDetailDagWeb: React.FC = () => {

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
    </XFlow>
  )
}

export default WorkspaceServerlessWorkflowInstanceDetailDagWeb;

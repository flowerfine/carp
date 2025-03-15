import React from "react";
import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import X6Menubar from "@/components/X6/Menubar";
import X6Toolbar from "@/components/X6/Toolbar";
import Dnd from "@/pages/Workspace/ServerlessWorkflow/Instance/dnd";
import {Connect} from "@/pages/Workspace/ServerlessWorkflow/Instance/connect";
import {InitNode} from "@/pages/Workspace/ServerlessWorkflow/Instance/init-shade";
import {SERVERLESS_WORKFLOW_EDGE} from "@/components/X6/Shape/ServerlessWorkflowNode";

const Page: React.FC = () => {
  return (
    <XFlow>
      <X6Layout
        menubar={<X6Menubar/>}
        toolbar={<X6Toolbar/>}
        dnd={<Dnd/>}
        body={(
          <>
            <XFlowGraph
              pannable
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
      <InitNode/>
      <Connect/>
    </XFlow>
  );
};

export default Page;

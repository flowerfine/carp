import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import {X6MindNode} from "@/pages/Workspace/X6/mind/shape";

const X6PipelineWeb = () => {
  return (
    <XFlow>
      <X6Layout
        body={(
          <XFlowGraph
            pannable
            connectionOptions={{
              connectionPoint: 'anchor'
            }}
          />
        )}
      />
      <X6MindNode/>
    </XFlow>
  );
};

export default X6PipelineWeb;

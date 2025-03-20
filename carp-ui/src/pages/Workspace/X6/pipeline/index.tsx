import {XFlow, XFlowGraph} from '@antv/xflow';
import X6Layout from "@/components/X6/Layout";
import {PIPELINE_EDGE} from "@/pages/Workspace/X6/pipeline/shape";
import {InitNode} from "@/pages/Workspace/X6/pipeline/init-node";

const X6PipelineWeb = () => {
  return (
    <XFlow>
      <X6Layout
        body={(
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
              validateMagnet({magnet}) {
                return magnet.getAttribute('port-group') !== 'top';
              },
            }}
            connectionEdgeOptions={{
              shape: PIPELINE_EDGE,
              animated: true,
              zIndex: -1,
            }}
          />
        )}
      />
      <InitNode />
    </XFlow>
  );
};

export default X6PipelineWeb;

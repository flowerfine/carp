import {XFlow, XFlowGraph} from '@antv/xflow';
import {InitNode} from "./init-shade";
import styles from './index.less';
import {CICD_CONNECTOR, CICD_EDGE} from "@/components/Flow/Node/CICDNode";
import X6ControlMinimap from "@/components/X6/Control";
import X6GridSnapline from "@/components/X6/Grid";
import X6HistoryClipboard from "@/components/X6/History";
import X6Layout from "@/components/X6/Layout";

const Page = () => {
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
              connector: CICD_CONNECTOR,
              validateMagnet({magnet}) {
                return magnet.getAttribute('port-group') !== 'top';
              },
            }}
            connectionEdgeOptions={{
              shape: CICD_EDGE,
              animated: true,
              zIndex: -1,
            }}
          />
        )}
      />
      <InitNode/>
    </XFlow>
  );
};

export default Page;

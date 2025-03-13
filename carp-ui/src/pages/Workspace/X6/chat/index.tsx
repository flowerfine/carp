import {XFlow, XFlowGraph} from '@antv/xflow';
import {InitNode} from "./init-shade";
import styles from './index.less';
import {FLOWISE_CONNECTOR, FLOWISE_EDGE} from "@/components/Flow/Node/FlowiseNode";

const Page = () => {
  return (
    <XFlow>
      <div className={styles.page}>
        <div className={styles.container}>
          <div className={styles.center}>
            <div className={styles.graph}>
              <XFlowGraph
                pannable
                connectionOptions={{
                  snap: true,
                  allowBlank: false,
                  allowLoop: false,
                  highlight: true,
                  connectionPoint: 'anchor',
                  anchor: 'center',
                  connector: FLOWISE_CONNECTOR,
                  validateMagnet({magnet}) {
                    return magnet.getAttribute('port-group') !== 'top';
                  },
                }}
                connectionEdgeOptions={{
                  shape: FLOWISE_EDGE,
                  animated: true,
                  zIndex: -1,
                }}
              />
              <InitNode/>
            </div>
          </div>
        </div>
      </div>
    </XFlow>
  );
};

export default Page;

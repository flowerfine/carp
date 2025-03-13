import {XFlow, XFlowGraph} from '@antv/xflow';
import {InitNode} from "./init-shade";
import styles from './index.less';
import {CICD_CONNECTOR, CICD_EDGE} from "@/components/Flow/Node/CICDNode";

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
              <InitNode/>
            </div>
          </div>
        </div>
      </div>
    </XFlow>
  );
};

export default Page;

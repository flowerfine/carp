import {Clipboard, Control, XFlow, XFlowGraph} from '@antv/xflow';
import {Connect} from './connect';
import {Dnd} from './dnd/dnd';
import {Toolbar} from './toolbar';
import {InitNode} from "./init-shade";
import styles from './index.less';
import {PROCESS_CONNECTOR, PROCESS_EDGE} from "@/components/Flow/Node/ProcessNode";

const Page = () => {
  return (
    <XFlow>
      <div className={styles.page}>
        <div className={styles.container}>
          <div className={styles.left}>
            <div className={styles.leftTop}>算子组件库</div>
            <Dnd/>
          </div>
          <div className={styles.center}>
            <div className={styles.toolbar}>
              <Toolbar/>
            </div>
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
                  connector: PROCESS_CONNECTOR,
                  validateMagnet({magnet}) {
                    return magnet.getAttribute('port-group') !== 'top';
                  },
                }}
                connectionEdgeOptions={{
                  shape: PROCESS_EDGE,
                  animated: true,
                  zIndex: -1,
                }}
              />
              <InitNode/>
              <Clipboard/>
              <Connect/>
              <div className={styles.controlTool}>
                <Control
                  items={['zoomOut', 'zoomTo', 'zoomIn', 'zoomToFit', 'zoomToOrigin']}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </XFlow>
  );
};

export default Page;

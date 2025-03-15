import {XFlow, XFlowGraph, Clipboard, Control, Background} from '@antv/xflow';

import { ConfigDrawer } from './config-drawer';
import { Connect } from './connect';
import { Dnd } from './dnd/dnd';
import styles from './index.less';
import { InitShape } from './node';
import { DAG_EDGE, DAG_CONNECTOR } from './shape';
import { Toolbar } from './toolbar';
import X6ControlMinimap from "@/components/X6/Control";
import X6GridSnapline from "@/components/X6/Grid";
import X6HistoryClipboard from "@/components/X6/History";

const Page = () => {
  return (
    <XFlow>
      <div className={styles.page}>
        <div className={styles.container}>
          <div className={styles.left}>
            <div className={styles.leftTop}>算子组件库</div>
            <Dnd />
          </div>
          <div className={styles.center}>
            <div className={styles.toolbar}>
              <Toolbar />
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
                  connector: DAG_CONNECTOR,
                  validateMagnet({ magnet }) {
                    return magnet.getAttribute('port-group') !== 'top';
                  },
                }}
                connectionEdgeOptions={{
                  shape: DAG_EDGE,
                  animated: true,
                  zIndex: -1,
                }}
              />
              <InitShape />
              <Clipboard />
              <Connect />
              <X6ControlMinimap/>
              <X6GridSnapline/>
              <X6HistoryClipboard/>
            </div>
          </div>
        </div>
        <ConfigDrawer />
      </div>
    </XFlow>
  );
};

export default Page;

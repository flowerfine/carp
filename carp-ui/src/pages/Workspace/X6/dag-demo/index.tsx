import { XFlow, XFlowGraph, Clipboard, Control } from '@antv/xflow';

import { Dnd } from './dnd/dnd';
import styles from './index.less';
import { DAG_EDGE, DAG_CONNECTOR } from './shape';

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

            <div className={styles.graph}>
              <XFlowGraph
                pannable
              />

              

              <Clipboard />
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

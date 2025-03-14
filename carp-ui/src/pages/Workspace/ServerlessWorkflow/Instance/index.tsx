import {XFlow, XFlowGraph} from '@antv/xflow';
import Dnd from "./dnd";
import {Connect} from './connect';
import {InitNode} from "./init-shade";
import styles from './index.less';
import {SERVERLESS_WORKFLOW_EDGE} from "@/components/X6/Shape/ServerlessWorkflowNode";
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
            <Dnd/>
          </div>
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
                  connector: 'smooth',
                }}
                connectionEdgeOptions={{
                  shape: SERVERLESS_WORKFLOW_EDGE,
                  animated: true,
                  zIndex: -1,
                }}
              />
              <InitNode/>
              <Connect/>
              <X6ControlMinimap/>
              <X6GridSnapline/>
              <X6HistoryClipboard/>
            </div>
          </div>
        </div>
      </div>
    </XFlow>
  );
};

export default Page;

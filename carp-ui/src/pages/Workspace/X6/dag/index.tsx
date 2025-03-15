import {XFlow, XFlowGraph} from '@antv/xflow';
import {Connect} from './connect';
import {Dnd} from './dnd/dnd';
import {InitShape} from './node';
import {DAG_CONNECTOR, DAG_EDGE} from './shape';
import X6Layout from "@/components/X6/Layout";
import {ConfigDrawer} from "@/pages/Workspace/X6/dag/config-drawer";
import {Toolbar} from "@/pages/Workspace/X6/dag/toolbar";

const Page = () => {
  return (
    <XFlow>
      <X6Layout
        toolbar={<Toolbar/>}
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
                validateMagnet({magnet}) {
                  return magnet.getAttribute('port-group') !== 'top';
                },
              }}
              connectionEdgeOptions={{
                shape: DAG_EDGE,
                animated: true,
                zIndex: -1,
              }}
            />
            <InitShape/>
            <Connect/>
            <ConfigDrawer/>
          </>
        )}
      />
    </XFlow>
  );
};

export default Page;

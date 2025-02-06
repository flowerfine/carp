import {PageContainer} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";
import {WorkspaceOrcaAPI} from "@/services/workspace/orca/typings";
import {Background, Control, ControlEnum, Grid, Minimap, Snapline, XFlow, XFlowGraph} from "@antv/xflow";
import styles from './index.less';

export type ScheduleOrcaState = {
  visiable: boolean;
  data?: WorkspaceOrcaAPI.OrcaPipelineExecution | null;
}

const WorkspaceOrcaInstanceDetailWeb: React.FC = () => {
  const intl = useIntl();

  return (
    <PageContainer
      title={intl.formatMessage({id: 'menu.workspace.orca.detail'})}
      onBack={() => history.back()}
    >
      <XFlow>
        <div className={styles.page}>
          <div className={styles.container}>
            <div className={styles.center}>
              <div className={styles.graph}>
                <XFlowGraph zoomable minScale={0.5}/>
                <Grid type="mesh" options={{color: '#ccc', thickness: 1}}/>
                <Snapline sharp/>
                <Background/>
                <div className={styles.scaleToolbar}>
                  <Control
                    items={[ControlEnum.ZoomToOrigin, ControlEnum.ZoomToFit, ControlEnum.ZoomIn, ControlEnum.ZoomTo, ControlEnum.ZoomOut]}
                    direction={'vertical'}
                  />
                </div>
                <div className={styles.minimap}>
                  <Minimap/>
                </div>
              </div>
            </div>
          </div>
        </div>
      </XFlow>
    </PageContainer>
  )
}

export default WorkspaceOrcaInstanceDetailWeb

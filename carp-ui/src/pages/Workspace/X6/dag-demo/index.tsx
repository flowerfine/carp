import {PageContainer} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";
import {Background, Control, ControlEnum, Grid, Minimap, Snapline, XFlow, XFlowGraph} from "@antv/xflow";
import styles from './index.less';

const DagDemoWeb: React.FC = () => {
  const intl = useIntl();

  return (
    <PageContainer
      title={intl.formatMessage({id: 'menu.workspace.x6.dag-demo'})}
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

export default DagDemoWeb

import {PageContainer} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";

const WorkspaceX6DagWeb: React.FC = () => {
  const intl = useIntl();

  return (
    <PageContainer
      title={intl.formatMessage({ id: 'menu.workspace.x6.dag' })}
      content={intl.formatMessage({ id: 'menu.workspace.x6.dag.desc' })}
    >

      <div>DAG 数据加工</div>
    </PageContainer>
  )
}

export default WorkspaceX6DagWeb

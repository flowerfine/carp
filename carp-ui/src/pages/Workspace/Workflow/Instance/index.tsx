import {useRef} from "react";
import {ActionType, PageContainer, ProFormInstance} from "@ant-design/pro-components";
import {useIntl} from "@umijs/max";

const WorkspaceWorkflowInstanceWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();

  return (
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.workflow.instance.desc' })}>
      <div>WorkspaceWorkflowInstanceWeb</div>
    </PageContainer>
  )
}

export default WorkspaceWorkflowInstanceWeb;

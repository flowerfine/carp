import React, {useRef} from "react";
import {useAccess, useIntl} from "@umijs/max";
import {ActionType, PageContainer} from "@ant-design/pro-components";

const WorkspaceScheduleConfig: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();

  return (
    <PageContainer content={intl.formatMessage({id: 'menu.workspace.schedule.config.desc'})}>
      <div>调度配置</div>
    </PageContainer>
  )
}

export default WorkspaceScheduleConfig

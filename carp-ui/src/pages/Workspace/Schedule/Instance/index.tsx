import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import { ActionType, PageContainer, ProTable } from "@ant-design/pro-components";
import { useIntl } from "@umijs/max";
import { useRef } from "react";

const WorkspaceScheduleInstance: React.FC = () => {
    const intl = useIntl();
    const actionRef = useRef<ActionType>();

    return (
        <PageContainer content={intl.formatMessage({ id: 'menu.workspace.schedule.instance.desc' })}>
            <div>WorkspaceScheduleInstance</div>
        </PageContainer>
    )
}

export default WorkspaceScheduleInstance
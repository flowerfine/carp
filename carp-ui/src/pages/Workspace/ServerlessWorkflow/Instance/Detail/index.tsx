import React, {useState} from "react";
import {Button, Dropdown, Space, Tag} from "antd";
import {ExportOutlined, SyncOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-components";
import {history, useIntl, useLocation} from "@umijs/max";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import WorkspaceServerlessWorkflowInstanceDetailDagWeb from "@/pages/Workspace/ServerlessWorkflow/Instance/Detail/Dag";

const WorkspaceServerlessWorkflowInstanceDetailWeb: React.FC = () => {
  const intl = useIntl();
  const urlParams = useLocation();
  const workflowInstance = urlParams.state as WorkspaceWorkflowAPI.WorkflowInstance;
  const [tabKey, setTabKey] = useState<string>('canvas');

  const items = [
    {
      key: '1',
      label: '1st item',
    },
    {
      key: '2',
      label: '2nd item',
    },
    {
      key: '3',
      label: '3rd item',
    },
  ];

  const renderChild = () => {
    if (tabKey === 'canvas') {
      return <WorkspaceServerlessWorkflowInstanceDetailDagWeb/>
    } else if (tabKey === 'input&output') {
      return <div>input&output tab</div>
    }
    return <WorkspaceServerlessWorkflowInstanceDetailDagWeb/>
  }

  return (
    <PageContainer
      title={(
        <Space>
          {intl.formatMessage({id: 'menu.workspace.serverless-workflow.instance.detail'})}
          <Tag>{workflowInstance.status}</Tag>
        </Space>
      )}
      content={workflowInstance.uuid}
      onBack={() => history.back()}
      fixedHeader
      header={{
        extra: [
          <Button key="defition"
                  type={"link"}
                  icon={<ExportOutlined/>}
                  iconPosition={"end"}
          >
            {intl.formatMessage({id: 'pages.workspace.workflow.instance.detail.buttton.definition'})}
          </Button>,
          <Button key="refresh"
                  icon={<SyncOutlined/>}
          >
            {intl.formatMessage({id: 'pages.workspace.workflow.instance.detail.buttton.refresh'})}
          </Button>,
          <Dropdown.Button menu={{items: items, onClick: () => {}}}>
            {intl.formatMessage({id: 'pages.workspace.workflow.instance.detail.buttton.actions'})}
          </Dropdown.Button>
        ]
      }}
      tabList={[
        {
          key: 'canvas',
          tab: intl.formatMessage({ id: 'pages.workspace.workflow.instance.detail.tab.canvas' }),
        },
        {
          key: 'input&output',
          tab: 'Input&Output',
        }
      ]}
      onTabChange={tabKey => setTabKey(tabKey)}
      tabProps={{
        centered: true,
      }}
    >
      {renderChild()}
    </PageContainer>
  )
}

export default WorkspaceServerlessWorkflowInstanceDetailWeb;

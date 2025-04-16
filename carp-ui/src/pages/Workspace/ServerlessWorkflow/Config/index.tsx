import React, {useRef, useState} from "react";
import {Button, Space, Table, Tooltip} from "antd";
import {FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {
  ServerlessWorkflowDefinitionService
} from "@/services/workspace/workflow/serverless-workflow-definition.service";

export type ServerlessWorkflowDefinitionState = {
  visiable: boolean;
  data?: WorkspaceWorkflowAPI.ServerlessWorkflowDefinition | null;
}

const WorkspaceServerlessWorkflowConfigWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition[]>([]);
  const [workflowDefinitionFormData, setWorkflowDefinitionFormData] = useState<ServerlessWorkflowDefinitionState>({ visiable: false, data: null });

  const onDetailClick = (record: WorkspaceWorkflowAPI.ServerlessWorkflowDefinition) => {
    history.push('/workspace/serverless-workflow/config/dag', record);
  };

  const columns: ProColumns<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.namespace' }),
      dataIndex: 'namespace'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.type' }),
      dataIndex: 'type'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.name' }),
      dataIndex: 'name'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.uuid' }),
      dataIndex: 'uuid'
    },
    {
      title: intl.formatMessage({ id: 'app.common.data.remark' }),
      dataIndex: 'remark',
      valueType: 'textarea',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'app.common.data.createTime' }),
      dataIndex: 'createTime',
      hideInSearch: true,
      width: 180,
    },
    {
      title: intl.formatMessage({ id: 'app.common.data.updateTime' }),
      dataIndex: 'updateTime',
      hideInSearch: true,
      width: 180,
    },
    {
      title: intl.formatMessage({ id: 'app.common.operate.label' }),
      dataIndex: 'actions',
      valueType: 'option',
      align: 'center',
      width: 120,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.more.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined />}
              onClick={() => onDetailClick(record)}
            />
          </Tooltip>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.serverless-workflow.config.desc' })}>
      <ProTable<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>
        search={{
          labelWidth: 'auto',
          span: { xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4 },
        }}
        rowKey="id"
        actionRef={actionRef}
        formRef={formRef}
        columns={columns}
        pagination={{ showQuickJumper: true, showSizeChanger: true, defaultPageSize: 10 }}
        rowSelection={{
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT, Table.SELECTION_NONE],
          fixed: true,
          onChange(_, selectedRows, info) {
            setSelectedRows(selectedRows);
          },
        }}
        request={(params, sorter, filter) => {
          return ServerlessWorkflowDefinitionService.page({...params, namespace: 'default'});
        }}
      />
    </PageContainer>
  )
}

export default WorkspaceServerlessWorkflowConfigWeb

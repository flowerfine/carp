import React, {useRef, useState} from "react";
import {Button, Space, Table, Tooltip} from "antd";
import {FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowService} from "@/services/workspace/workflow/workflow.service";

export type WorkflowDefinitionState = {
  visiable: boolean;
  data?: WorkspaceWorkflowAPI.WorkflowDefinition | null;
}

const WorkspaceWorkflowDefinitionWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceWorkflowAPI.WorkflowDefinition[]>([]);
  const [workflowDefinitionFormData, setWorkflowDefinitionFormData] = useState<WorkflowDefinitionState>({ visiable: false, data: null });

  const onDetailClick = (record: WorkspaceWorkflowAPI.WorkflowDefinition) => {
    history.push('/workspace/schedule/instance', record);
  };

  const columns: ProColumns<WorkspaceWorkflowAPI.WorkflowDefinition>[] = [
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
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.schedule.config.desc' })}>
      <ProTable<WorkspaceWorkflowAPI.WorkflowDefinition>
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
          return WorkflowService.page({...params, namespace: 'default'});
        }}
      />
    </PageContainer>
  )
}

export default WorkspaceWorkflowDefinitionWeb

import React, {useRef, useState} from "react";
import {Button, Space, Table, Tooltip} from "antd";
import {FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useIntl} from "@umijs/max";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {ServerlessWorkflowInstanceService} from "@/services/workspace/workflow/serverless-workflow-instance.service";

const WorkspaceServerlessWorkflowInstanceWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceWorkflowAPI.ServerlessWorkflowInstance[]>([]);

  const onDetailClick = (record: WorkspaceWorkflowAPI.ServerlessWorkflowInstance) => {
    history.push('/workspace/serverless-workflow/instance/detail', record);
  };

  const columns: ProColumns<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.namespace' }),
      dataIndex: 'namespace'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.uuid' }),
      dataIndex: 'uuid',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.definition.type' }),
      dataIndex: 'workflowInstance',
      renderText: (dom, record) => {
        return record.workflowInstance?.type;
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.status' }),
      dataIndex: 'status'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.startTime' }),
      dataIndex: 'startTime',
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.endTime' }),
      dataIndex: 'endTime'
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
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.serverless-workflow.instance.desc' })}>
      <ProTable<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>
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
          return ServerlessWorkflowInstanceService.page({...params, namespace: 'default'});
        }}
      />
    </PageContainer>
  )
}

export default WorkspaceServerlessWorkflowInstanceWeb

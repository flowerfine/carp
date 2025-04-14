import React, {useRef, useState} from "react";
import {Button, message, Modal, Space, Table, Tag, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined, FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {useIntl, history} from "@umijs/max";
import {MonitorAlertRuleService} from "@/services/workspace/monitor/alert-rule.service";
import {WorkspaceMonitorAPI} from "@/services/workspace/monitor/typings";
import {MonitorAlertMessageService} from "@/services/workspace/monitor/alert-message.service";

const WorkspaceMonitorAlertRuleWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();

  const [selectedRows, setSelectedRows] = useState<WorkspaceMonitorAPI.AlertMessage[]>([]);

  const onDetailClick = (record: WorkspaceMonitorAPI.AlertMessage) => {
    const param: WorkspaceMonitorAPI.AlertRulePageParam = {
      namespace: record.namespace,
      uuid: record.ruleId
    }
    history.push('/workspace/monitor/alert/rule', param);
  };

  const columns: ProColumns<WorkspaceMonitorAPI.AlertMessage>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.namespace' }),
      dataIndex: 'namespace',
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.ruleName' }),
      dataIndex: 'ruleName',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.resourceType' }),
      dataIndex: 'resourceType',
      ellipsis: true,
      copyable: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.resourceId' }),
      dataIndex: 'resourceId',
      ellipsis: true,
      copyable: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.fingerprint' }),
      dataIndex: 'fingerprint',
      ellipsis: true,
      copyable: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.status' }),
      dataIndex: 'status',
      render: (dom, record, index) => {
        return <Tag>{record.status.label}</Tag>
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.startTime' }),
      dataIndex: 'startTime',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.endTime' }),
      dataIndex: 'endTime',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.count' }),
      dataIndex: 'count',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.summary' }),
      dataIndex: 'summary',
      hideInSearch: true,
      ellipsis: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.monitor.alert.message.description' }),
      dataIndex: 'description',
      hideInSearch: true,
      ellipsis: true,
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
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.delete.label' })}>
            <Button
              shape="default"
              type="link"
              danger
              icon={<DeleteOutlined />}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({ id: 'app.common.operate.delete.confirm.title' }),
                  content: intl.formatMessage({ id: 'app.common.operate.delete.confirm.content' }),
                  okText: intl.formatMessage({ id: 'app.common.operate.confirm.label' }),
                  okButtonProps: { danger: true },
                  cancelText: intl.formatMessage({ id: 'app.common.operate.cancel.label' }),
                  onOk() {
                    MonitorAlertMessageService.delete(record).then((response) => {
                      if (response.success) {
                        message.success(intl.formatMessage({ id: 'app.common.operate.delete.success' }));
                        actionRef.current?.reload();
                      }
                    });
                  },
                });
              }}
            />
          </Tooltip>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.monitor.alert.message.desc' })}>
      <ProTable<WorkspaceMonitorAPI.AlertMessage>
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
          return MonitorAlertMessageService.page({...params, namespace: 'default'})
        }}
        toolbar={{
          actions: [
            <Button
              key="del"
              type="default"
              danger
              disabled={selectedRows.length < 1}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({ id: 'app.common.operate.delete.confirm.title' }),
                  content: intl.formatMessage({ id: 'app.common.operate.delete.confirm.content' }),
                  okText: intl.formatMessage({ id: 'app.common.operate.confirm.label' }),
                  okButtonProps: { danger: true },
                  cancelText: intl.formatMessage({ id: 'app.common.operate.cancel.label' }),
                  onOk() {
                    MonitorAlertMessageService.deleteBatch(selectedRows).then((response) => {
                      if (response.success) {
                        message.success(intl.formatMessage({ id: 'app.common.operate.delete.success' }));
                        actionRef.current?.reload();
                      }
                    });
                  },
                });
              }}
            >
              {intl.formatMessage({ id: 'app.common.operate.delete.label' })}
            </Button>
          ],
        }}
      />
    </PageContainer>
  )
}

export default WorkspaceMonitorAlertRuleWeb;

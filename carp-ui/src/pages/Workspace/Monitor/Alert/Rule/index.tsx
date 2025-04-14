import React, {useEffect, useRef, useState} from "react";
import {Button, message, Modal, Space, Table, Tag, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined, FileSearchOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useIntl, useLocation} from "@umijs/max";
import {MonitorAlertRuleService} from "@/services/workspace/monitor/alert-rule.service";
import {WorkspaceMonitorAPI} from "@/services/workspace/monitor/typings";

const WorkspaceMonitorAlertRuleWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceMonitorAPI.AlertRule[]>([]);

  const urlParams = useLocation();
  const alertRulePageParam = urlParams.state as WorkspaceMonitorAPI.AlertRulePageParam;

  useEffect(() => {
    if (alertRulePageParam) {
      formRef.current?.setFieldValue("namespace", alertRulePageParam.namespace)
      formRef.current?.setFieldValue("uuid", alertRulePageParam.uuid)
    } else {
      formRef.current?.setFieldValue("namespace", "default")
    }
    formRef.current?.submit()
    actionRef.current?.reload()
  }, []);

  const onDetailClick = (record: WorkspaceMonitorAPI.AlertRule) => {
    history.push('/workspace/schedule/instance', record);
  };

  const columns: ProColumns<WorkspaceMonitorAPI.AlertRule>[] = [
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.namespace'}),
      dataIndex: 'namespace',
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.name'}),
      dataIndex: 'name',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.uuid'}),
      dataIndex: 'uuid',
      ellipsis: true,
      copyable: true,
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.isEnabled'}),
      dataIndex: 'isEnabled',
      render: (dom, record, index) => {
        return <Tag>{record.isEnabled.label}</Tag>
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.level'}),
      dataIndex: 'level',
      hideInSearch: true,
      render: (dom, record, index) => {
        return record.level.label
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.promql'}),
      dataIndex: 'promql',
      hideInSearch: true,
      ellipsis: true,
      copyable: true,
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.waitFor'}),
      dataIndex: 'waitFor',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.summary'}),
      dataIndex: 'summary',
      hideInSearch: true,
      ellipsis: true,
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.monitor.alert.rule.description'}),
      dataIndex: 'description',
      hideInSearch: true,
      ellipsis: true,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.remark'}),
      dataIndex: 'remark',
      valueType: 'textarea',
      hideInSearch: true,
      ellipsis: true,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.createTime'}),
      dataIndex: 'createTime',
      hideInSearch: true,
      width: 180,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.updateTime'}),
      dataIndex: 'updateTime',
      hideInSearch: true,
      width: 180,
    },
    {
      title: intl.formatMessage({id: 'app.common.operate.label'}),
      dataIndex: 'actions',
      valueType: 'option',
      align: 'center',
      width: 120,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.edit.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined/>}
              onClick={() => {
                // setScheduleConfigFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.more.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined/>}
              onClick={() => onDetailClick(record)}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.delete.label'})}>
            <Button
              shape="default"
              type="link"
              danger
              icon={<DeleteOutlined/>}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                  content: intl.formatMessage({id: 'app.common.operate.delete.confirm.content'}),
                  okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                  okButtonProps: {danger: true},
                  cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                  onOk() {
                    MonitorAlertRuleService.delete(record).then((response) => {
                      if (response.success) {
                        message.success(intl.formatMessage({id: 'app.common.operate.delete.success'}));
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
    <PageContainer content={intl.formatMessage({id: 'menu.workspace.monitor.alert.rule.desc'})}>
      <ProTable<WorkspaceMonitorAPI.AlertRule>
        search={{
          labelWidth: 'auto',
          span: {xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4},
        }}
        rowKey="id"
        actionRef={actionRef}
        formRef={formRef}
        columns={columns}
        pagination={{showQuickJumper: true, showSizeChanger: true, defaultPageSize: 10}}
        rowSelection={{
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT, Table.SELECTION_NONE],
          fixed: true,
          onChange(_, selectedRows, info) {
            setSelectedRows(selectedRows);
          },
        }}
        request={(params, sorter, filter) => {
          if (params.namespace) {
            return MonitorAlertRuleService.page(params)
          }
          return Promise.reject()
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                // setScheduleConfigFormData({ visiable: true, data: null });
              }}
            >
              {intl.formatMessage({id: 'app.common.operate.new.label'})}
            </Button>,

            <Button
              key="del"
              type="default"
              danger
              disabled={selectedRows.length < 1}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({id: 'app.common.operate.delete.confirm.title'}),
                  content: intl.formatMessage({id: 'app.common.operate.delete.confirm.content'}),
                  okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                  okButtonProps: {danger: true},
                  cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                  onOk() {
                    MonitorAlertRuleService.deleteBatch(selectedRows).then((response) => {
                      if (response.success) {
                        message.success(intl.formatMessage({id: 'app.common.operate.delete.success'}));
                        actionRef.current?.reload();
                      }
                    });
                  },
                });
              }}
            >
              {intl.formatMessage({id: 'app.common.operate.delete.label'})}
            </Button>
          ],
        }}
      />
    </PageContainer>
  )
}

export default WorkspaceMonitorAlertRuleWeb;

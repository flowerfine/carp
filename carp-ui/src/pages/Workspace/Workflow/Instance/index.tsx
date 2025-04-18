import React, {useEffect, useRef, useState} from "react";
import {Button, message, Modal, Select, Space, Table, Tag, Tooltip, Typography} from "antd";
import {DeleteOutlined, EditOutlined, PauseCircleOutlined, PlayCircleOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useIntl, useLocation} from "@umijs/max";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import {WorkflowInstanceService} from "@/services/workspace/workflow/workflow-instance.service";
import WorkflowInstanceForm from "./components/WorkflowInstanceForm";

export type WorkflowInstanceState = {
  visiable: boolean;
  data?: {
    workflowDefinition: WorkspaceWorkflowAPI.WorkflowDefinition;
    workflowInstance?: WorkspaceWorkflowAPI.WorkflowInstance;
  };
}

const WorkspaceWorkflowInstanceWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const urlParams = useLocation();
  const workflowDefinition = urlParams.state as WorkspaceWorkflowAPI.WorkflowDefinition;
  const [selectedRows, setSelectedRows] = useState<WorkspaceWorkflowAPI.WorkflowInstance[]>([]);

  const [namespace, setNamesapce] = useState<string>("default");
  const [workflowDefinitions, setWorkflowDefinitions] = useState<WorkspaceWorkflowAPI.WorkflowDefinition[]>([]);

  const [workflowInstanceFormData, setWorkflowInstanceFormData] = useState<WorkflowInstanceState>({
    visiable: false,
    data: {workflowDefinition: workflowDefinition}
  });

  useEffect(() => {
    setNamesapce(workflowDefinition.namespace)
    WorkflowDefinitionService.list({namespace: workflowDefinition.namespace}).then((result) => {
      if (result.data) {
        setWorkflowDefinitions(result.data)
      }
    })

    formRef.current?.setFieldValue("namespace", workflowDefinition.namespace)
    formRef.current?.setFieldValue("workflowDefinitionId", workflowDefinition.id)
    formRef.current?.submit()
    actionRef.current?.reload()
  }, [namespace]);

  const columns: ProColumns<WorkspaceWorkflowAPI.WorkflowInstance>[] = [
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.namespace'}),
      dataIndex: 'namespace',
      fieldProps: {
        allowClear: false,
        disabled: true
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.workflowDefinition'}),
      dataIndex: 'workflowDefinitionId',
      hideInTable: true,
      renderFormItem: (item, {defaultRender, ...rest}, form) => {
        return (
          <Select
            showSearch={true}
            allowClear={false}
            disabled={true}
            optionFilterProp="label"
            filterOption={(input, option) =>
              (option!.children as unknown as string).toLowerCase().includes(input.toLowerCase())
            }
          >
            {workflowDefinitions.map((item) => {
              return (
                <Select.Option key={item.id} value={item.id}>
                  {item.name}
                </Select.Option>
              );
            })}
          </Select>
        );
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.uuid'}),
      dataIndex: 'uuid'
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.params'}),
      dataIndex: 'params',
      render: (dom, entity) => {
        return (
          <>
            {workflowDefinition.engine.value == 'temporal' && (
              <>
                <Typography.Text strong>
                  {intl.formatMessage({id: 'pages.workspace.workflow.instance.engine.temporal.queue'})}
                </Typography.Text>
                {' : ' + entity.params?.queue}
                <br/>
              </>
            )}
            {workflowDefinition.engine.value == 'internal' && (
              <></>
            )}
            {entity.params?.inputs && (
              <>
                <Typography.Text strong>
                  {intl.formatMessage({id: 'pages.workspace.workflow.instance.params.inputs'})}
                </Typography.Text>
                {' : ' + entity.params?.inputs}
                <br/>
              </>
            )}
            {entity.params?.variables && (
              <>
                <Typography.Text strong>
                  {intl.formatMessage({id: 'pages.workspace.workflow.instance.params.variables'})}
                </Typography.Text>
                {' : ' + entity.params?.variables}
                <br/>
              </>
            )}
          </>
        )
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.status'}),
      dataIndex: 'status',
      render: (dom, entity) => {
        return <Tag>{entity.status.label}</Tag>
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpWorkflowInstanceStatus)
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.instance.trigger'}),
      dataIndex: 'trigger',
      render: (dom, entity) => {
        return (
          <>
            {workflowDefinition.engine.value == 'temporal' && (
              <>
                {entity.trigger?.cron && (
                  <>
                    <Typography.Text strong>
                      {intl.formatMessage({id: 'pages.workspace.workflow.instance.trigger.engine.temporal.cron'})}
                    </Typography.Text>
                    {' : ' + entity.trigger?.cron}
                    <br/>
                  </>
                )}
              </>
            )}
            {workflowDefinition.engine.value == 'internal' && (
              <>
                {entity.trigger?.schedule?.timezone && (
                  <>
                    <Typography.Text strong>
                      {intl.formatMessage({id: 'pages.workspace.workflow.instance.trigger.engine.internal.schedule.timezone'})}
                    </Typography.Text>
                    {' : ' + entity.trigger?.schedule?.timezone}
                    <br/>
                  </>
                )}
                {entity.trigger?.schedule?.cron && (
                  <>
                    <Typography.Text strong>
                      {intl.formatMessage({id: 'pages.workspace.workflow.instance.trigger.engine.internal.schedule.cron'})}
                    </Typography.Text>
                    {' : ' + entity.trigger?.schedule?.cron}
                    <br/>
                  </>
                )}
                {entity.trigger?.schedule?.validTime && (
                  <>
                    <Typography.Text strong>
                      {intl.formatMessage({id: 'pages.workspace.workflow.instance.trigger.engine.internal.schedule.validTime'})}
                    </Typography.Text>
                    {' : ' + entity.trigger?.schedule?.validTime[0] + ' ~ ' + entity.trigger?.schedule?.validTime[1]}
                    <br/>
                  </>
                )}
              </>
            )}
          </>
        )
      }
    },
    {
      title: intl.formatMessage({id: 'app.common.data.remark'}),
      dataIndex: 'remark',
      valueType: 'textarea',
      hideInSearch: true
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
          <Tooltip title={record.status.value == 'running' ?
            intl.formatMessage({id: 'app.common.operate.stop.label'})
            : intl.formatMessage({id: 'app.common.operate.start.label'})}>
            <Button
              shape="default"
              type="link"
              icon={record.status.value == 'running' ? <PauseCircleOutlined/> : <PlayCircleOutlined/>}
              onClick={() => {
                Modal.confirm({
                  title: record.status.value == 'running' ?
                    intl.formatMessage({id: 'app.common.operate.stop.label'}) + intl.formatMessage({id: 'pages.workspace.workflow.instance'})
                    : intl.formatMessage({id: 'app.common.operate.start.label'}) + intl.formatMessage({id: 'pages.workspace.workflow.instance'}),
                  content: record.uuid,
                  okText: intl.formatMessage({id: 'app.common.operate.confirm.label'}),
                  cancelText: intl.formatMessage({id: 'app.common.operate.cancel.label'}),
                  onOk() {
                    record.status.value == 'running' ?
                      WorkflowInstanceService.stop(record).then((response) => {
                        if (response.success) {
                          message.success(intl.formatMessage({id: 'app.common.operate.stop.success'}));
                          actionRef.current?.reload();
                        }
                      })
                      : WorkflowInstanceService.start(record).then((response) => {
                        if (response.success) {
                          message.success(intl.formatMessage({id: 'app.common.operate.start.success'}));
                          actionRef.current?.reload();
                        }
                      });
                  },
                });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.edit.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined/>}
              onClick={() => {
                setWorkflowInstanceFormData({
                  visiable: true,
                  data: {workflowDefinition: workflowDefinition, workflowInstance: record}
                });
              }}
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
                    WorkflowInstanceService.delete(record).then((response) => {
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
    <PageContainer title={intl.formatMessage({id: 'menu.workspace.workflow.instance'})}
                   content={intl.formatMessage({id: 'menu.workspace.workflow.instance.desc'})}
                   onBack={() => history.back()}>
      <ProTable<WorkspaceWorkflowAPI.WorkflowInstance>
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
            return WorkflowInstanceService.page(params)
          }
          return Promise.reject()
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setWorkflowInstanceFormData({visiable: true, data: {workflowDefinition: workflowDefinition}});
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
                    WorkflowInstanceService.deleteBatch(selectedRows).then((response) => {
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

      {workflowInstanceFormData.visiable && (
        <WorkflowInstanceForm
          visible={workflowInstanceFormData.visiable}
          data={workflowInstanceFormData.data}
          onCancel={() => {
            setWorkflowInstanceFormData({visiable: false, data: {workflowDefinition: workflowDefinition}});
          }}
          onFinish={(values) => {
            setWorkflowInstanceFormData({visiable: false, data: {workflowDefinition: workflowDefinition}});
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  )
}

export default WorkspaceWorkflowInstanceWeb;

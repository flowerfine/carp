import React, { useEffect, useRef, useState } from "react";
import { Button, message, Modal, Select, Space, Table, Tooltip, Typography } from "antd";
import {DeleteOutlined, EditOutlined, PlayCircleOutlined} from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { useIntl, useLocation } from "@umijs/max";
import { WorkspaceWorkflowAPI } from "@/services/workspace/workflow/typings";
import { WorkflowDefinitionService } from "@/services/workspace/workflow/workflow-definition.service";
import { WorkflowInstanceService } from "@/services/workspace/workflow/workflow-instance.service";
import WorkflowInstanceForm from "./components/WorkflowInstanceForm";
import WorkflowInstanceStartForm from "@/pages/Workspace/Workflow/Instance/components/WorkflowInstanceStartForm";

export type WorkflowInstanceState = {
  visiable: boolean;
  data?: {
    workflowDefinition: WorkspaceWorkflowAPI.WorkflowDefinition;
    workflowInstance?: WorkspaceWorkflowAPI.WorkflowInstance;
  };
}

export type WorkflowInstanceStartState = {
  visiable: boolean;
  data?: WorkspaceWorkflowAPI.WorkflowInstance;
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
    data: { workflowDefinition: workflowDefinition }
  });
  const [workflowInstanceStartFormData, setWorkflowInstanceStartFormData] = useState<WorkflowInstanceStartState>({visiable: false});

  useEffect(() => {
    setNamesapce(workflowDefinition.namespace)
    WorkflowDefinitionService.list({ namespace: workflowDefinition.namespace }).then((result) => {
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
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.namespace' }),
      dataIndex: 'namespace',
      fieldProps: {
        allowClear: false
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.workflowDefinition' }),
      dataIndex: 'workflowDefinitionId',
      hideInTable: true,
      renderFormItem: (item, { defaultRender, ...rest }, form) => {
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
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.uuid' }),
      dataIndex: 'uuid'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.params' }),
      dataIndex: 'params',
      render: (dom, entity) => {
        if (workflowDefinition.engine.value == 'temporal') {
          return (
            <>
              <Typography.Text strong>
                {intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.queue' })}
              </Typography.Text>
              {' : ' + entity.params?.queue}
              <br />
              <Typography.Text strong>
                {intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.timezone' })}
              </Typography.Text>
              {' : ' + entity.params?.timezone}
              <br />
              <Typography.Text strong>
                {intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.expression' })}
              </Typography.Text>
              {' : ' + entity.params?.expression}
              <br />
              <Typography.Text strong>
                {intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.validTime' })}
              </Typography.Text>
              {' : ' + entity.params?.validTime[0] + ' ~ ' + entity.params?.validTime[1]}
              <br />
              <Typography.Text strong>
                {intl.formatMessage({ id: 'pages.workspace.workflow.instance.params.param' })}
              </Typography.Text>
              {' : ' + entity.params?.param}
            </>
          )
        }
        if (workflowDefinition.engine.value == 'internal') {
          return JSON.stringify(entity.params)
        }
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.instance.status' }),
      dataIndex: 'status'
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
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.start.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<PlayCircleOutlined />}
              onClick={() => {
                setWorkflowInstanceStartFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined />}
              onClick={() => {
                setWorkflowInstanceFormData({ visiable: true, data: { workflowDefinition: workflowDefinition, workflowInstance: record } });
              }}
            />
          </Tooltip>
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
                    WorkflowInstanceService.delete(record).then((response) => {
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
    <PageContainer title={intl.formatMessage({ id: 'menu.workspace.workflow.instance' })}
      content={intl.formatMessage({ id: 'menu.workspace.workflow.instance.desc' })}>
      <ProTable<WorkspaceWorkflowAPI.WorkflowInstance>
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
                setWorkflowInstanceFormData({ visiable: true, data: { workflowDefinition: workflowDefinition } });
              }}
            >
              {intl.formatMessage({ id: 'app.common.operate.new.label' })}
            </Button>,

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
                    WorkflowInstanceService.deleteBatch(selectedRows).then((response) => {
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

      {workflowInstanceFormData.visiable && (
        <WorkflowInstanceForm
          visible={workflowInstanceFormData.visiable}
          data={workflowInstanceFormData.data}
          onCancel={() => {
            setWorkflowInstanceFormData({ visiable: false, data: { workflowDefinition: workflowDefinition } });
          }}
          onFinish={(values) => {
            setWorkflowInstanceFormData({ visiable: false, data: { workflowDefinition: workflowDefinition } });
            actionRef.current?.reload();
          }}
        />
      )}

      {workflowInstanceStartFormData.visiable && (
        <WorkflowInstanceStartForm
          visible={workflowInstanceStartFormData.visiable}
          data={workflowInstanceStartFormData.data}
          onCancel={() => {
            setWorkflowInstanceStartFormData({ visiable: false });
          }}
          onFinish={(values) => {
            setWorkflowInstanceStartFormData({ visiable: false });
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  )
}

export default WorkspaceWorkflowInstanceWeb;

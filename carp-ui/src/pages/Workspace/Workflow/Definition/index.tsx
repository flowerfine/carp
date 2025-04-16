import React, {useEffect, useRef, useState} from "react";
import {Button, message, Modal, Space, Table, Tag, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {useIntl} from "@umijs/max";
import {DICT_TYPE} from "@/constants/dictType";
import {DictService} from "@/services/admin/system/dict.service";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import WorkflowDefinitionForm from "@/pages/Workspace/Workflow/Definition/components/WorkflowDefinitionForm";

export type WorkflowDefinitionState = {
  visiable: boolean;
  data?: WorkspaceWorkflowAPI.WorkflowDefinition | null;
}

const WorkspaceWorkflowDefinitionWeb: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceWorkflowAPI.WorkflowDefinition[]>([]);
  const [workflowDefinitionFormData, setWorkflowDefinitionFormData] = useState<WorkflowDefinitionState>({
    visiable: false,
    data: null
  });

  useEffect(() => {
    formRef.current?.setFieldValue("namespace", "default")
    formRef.current?.submit()
    actionRef.current?.reload()
  }, []);

  const columns: ProColumns<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>[] = [
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.definition.namespace'}),
      dataIndex: 'namespace',
      fieldProps: {
        allowClear: false
      }
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.definition.name'}),
      dataIndex: 'name'
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.workflow.definition.engine'}),
      dataIndex: 'engine',
      render: (dom, record, index) => {
        return <Tag>{record.engine.label}</Tag>
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpWorkflowEngineType)
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
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.edit.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined/>}
              onClick={() => {
                setWorkflowDefinitionFormData({visiable: true, data: record});
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
                    WorkflowDefinitionService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({id: 'menu.workspace.workflow.definition.desc'})}>
      <ProTable<WorkspaceWorkflowAPI.WorkflowDefinition>
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
            return WorkflowDefinitionService.page(params)
          }
          return Promise.reject()
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setWorkflowDefinitionFormData({visiable: true, data: null});
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
                    WorkflowDefinitionService.deleteBatch(selectedRows).then((response) => {
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

      {workflowDefinitionFormData.visiable && (
        <WorkflowDefinitionForm
          visible={workflowDefinitionFormData.visiable}
          data={workflowDefinitionFormData.data}
          onCancel={() => {
            setWorkflowDefinitionFormData({visiable: false, data: null});
          }}
          onFinish={(values) => {
            setWorkflowDefinitionFormData({visiable: false, data: null});
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  )

}

export default WorkspaceWorkflowDefinitionWeb;

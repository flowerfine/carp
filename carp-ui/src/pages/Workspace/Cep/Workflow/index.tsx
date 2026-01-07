import { useRef, useState } from 'react';
import { Button, message, Modal, Space, Table, Tooltip } from 'antd';
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from '@ant-design/icons';
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from '@ant-design/pro-components';
import { useIntl, history } from '@umijs/max';
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import CepWorkflowForm from './components/CepWorkflowForm';
import { CepWorkflowService } from '@/services/workspace/cep/cep-workflow.service';

export type CepWorkflowState = {
  visiable: boolean;
  data?: WorkspaceCepAPI.CepWorkflow;
}

export default () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceCepAPI.CepWorkflow[]>([]);
  const [cepWorkflowFormData, setCepWorkflowFormData] = useState<CepWorkflowState>({
    visiable: false
  });

  const onDetailClick = (record: WorkspaceCepAPI.CepWorkflow) => {
    history.push('/workspace/cep/workflow/detail', record);
  };

  const columns: ProColumns<WorkspaceCepAPI.CepWorkflow>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.namespace' }),
      dataIndex: 'namespace'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow.name' }),
      dataIndex: 'name',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow.uuid' }),
      dataIndex: 'uuid',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow.type' }),
      dataIndex: 'type',
      hideInSearch: true
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
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined />}
              onClick={() => {
                setCepWorkflowFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.more.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined />}
              onClick={() => onDetailClick(record)}
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
                    CepWorkflowService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.cep.rule.desc' })}>
      <ProTable<WorkspaceCepAPI.CepWorkflow>
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
          const queryParams: WorkspaceCepAPI.CepWorkflowPageParam = { ...params };
          return CepWorkflowService.page(queryParams);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setCepWorkflowFormData({ visiable: true });
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
                    CepWorkflowService.deleteBatch(selectedRows).then((response) => {
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

      {cepWorkflowFormData.visiable && (
        <CepWorkflowForm
          visible={cepWorkflowFormData.visiable}
          data={cepWorkflowFormData.data}
          onCancel={() => {
            setCepWorkflowFormData({ visiable: false });
          }}
          onFinish={(values) => {
            setCepWorkflowFormData({ visiable: false });
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  );
};

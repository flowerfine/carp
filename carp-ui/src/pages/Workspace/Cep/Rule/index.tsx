import { useRef, useState } from 'react';
import { Button, message, Modal, Space, Table, Tooltip } from 'antd';
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from '@ant-design/icons';
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from '@ant-design/pro-components';
import { useIntl, history } from '@umijs/max';
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import { CepRuleService } from '@/services/workspace/cep/cep-rule.service';
import CepRuleForm from './components/CepRuleForm';

export type CepRuleState = {
  visiable: boolean;
  data?: WorkspaceCepAPI.CepRule;
}

export default () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceCepAPI.CepRule[]>([]);
  const [cepRuleFormData, setCepRuleFormData] = useState<CepRuleState>({
    visiable: false
  });

  const onDetailClick = (record: WorkspaceCepAPI.CepRule) => {
    history.push('/workspace/cep/rule/detail', record);
  };

  const columns: ProColumns<WorkspaceCepAPI.CepRule>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.namespace' }),
      dataIndex: 'namespace'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.name' }),
      dataIndex: 'name'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.uuid' }),
      dataIndex: 'uuid',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.type' }),
      dataIndex: 'type',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.window' }),
      dataIndex: 'window',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.function' }),
      dataIndex: 'function',
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
                setCepRuleFormData({ visiable: true, data: record });
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
                    CepRuleService.delete(record).then((response) => {
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
      <ProTable<WorkspaceCepAPI.CepRule>
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
          const queryParams: WorkspaceCepAPI.CepRulePageParam = { ...params };
          return CepRuleService.page(queryParams);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setCepRuleFormData({ visiable: true });
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
                    CepRuleService.deleteBatch(selectedRows).then((response) => {
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

      {cepRuleFormData.visiable && (
        <CepRuleForm
          visible={cepRuleFormData.visiable}
          data={cepRuleFormData.data}
          onCancel={() => {
            setCepRuleFormData({ visiable: false });
          }}
          onFinish={(values) => {
            setCepRuleFormData({ visiable: false });
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  );
};

import React, { useEffect, useRef, useState } from "react";
import { ActionType, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { useIntl } from "@umijs/max";
import { WorkspaceCepAPI } from "@/services/workspace/cep/typings";
import { Button, Modal, Space, Table, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from "@ant-design/icons";
import { CepFunctionService } from "@/services/workspace/cep/cep-function.service";


type FunctionProps = {
  namespace: string;
  categoryId: number;
};

const FunctionListWeb: React.FC<FunctionProps> = (props) => {
  const { namespace, categoryId } = props;

  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceCepAPI.CepFunction[]>([]);

  useEffect(() => {
    actionRef.current?.reload();
  }, [props]);

  const columns: ProColumns<WorkspaceCepAPI.CepFunction>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.namespace' }),
      dataIndex: 'namespace'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.function.name' }),
      dataIndex: 'name'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.function.shortName' }),
      dataIndex: 'shortName'
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.workflow.cep.function.uuid' }),
      dataIndex: 'uuid',
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
                // setCepRuleFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.more.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined />}
            // onClick={() => onDetailClick(record)}
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
                    // CepRuleService.delete(record).then((response) => {
                    //   if (response.success) {
                    //     message.success(intl.formatMessage({ id: 'app.common.operate.delete.success' }));
                    //     actionRef.current?.reload();
                    //   }
                    // });
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
    <>
      <ProTable<WorkspaceCepAPI.CepFunction>
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
          const queryParams: WorkspaceCepAPI.CepFunctionPageParam = {
            ...params,
            namespace,
            categoryId,
          };
          return CepFunctionService.page(queryParams);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                // setCepRuleFormData({ visiable: true });
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
                    // CepRuleService.deleteBatch(selectedRows).then((response) => {
                    //   if (response.success) {
                    //     message.success(intl.formatMessage({ id: 'app.common.operate.delete.success' }));
                    //     actionRef.current?.reload();
                    //   }
                    // });
                  },
                });
              }}
            >
              {intl.formatMessage({ id: 'app.common.operate.delete.label' })}
            </Button>
          ],
        }}
      />
    </>
  )
};

export default FunctionListWeb;

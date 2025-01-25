import React, {useRef, useState} from 'react';
import {Button, message, Modal, Space, Table, Tag, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined, PlusOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {history, useAccess, useIntl} from "@umijs/max";
import {AdminSecurityAPI} from "@/services/admin/security/typings";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";
import {DeptService} from '@/services/admin/security/dept.service';
import SecurityDeptForm from './components/SecurityDeptForm';

export type SecurityDeptState = {
  visiable: boolean;
  data?: AdminSecurityAPI.SecDept | null;
  parent?: AdminSecurityAPI.SecDept | null;
}

const AdminSecurityDeptWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<AdminSecurityAPI.SecDept[]>([]);
  const [deptFormData, setDeptFormData] = useState<SecurityDeptState>({ visiable: false, data: null, parent: null });

  const columns: ProColumns<AdminSecurityAPI.SecDept>[] = [
    {
      title: intl.formatMessage({ id: 'pages.admin.security.dept.name' }),
      dataIndex: 'name',
      width: 200,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.dept.code' }),
      dataIndex: 'code',
      width: 200,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.dept.status' }),
      dataIndex: 'status',
      render: (dom, entity) => {
        return <Tag>{entity.status?.label}</Tag>;
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpSecDeptStatus)
      },
      width: 200,
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
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.new.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<PlusOutlined />}
              onClick={() =>
                setDeptFormData({ visiable: true, parent: record, data: null })
              }
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined />}
              onClick={() => {
                setDeptFormData({ visiable: true, data: record });
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
                    DeptService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({ id: 'menu.admin.security.dept.desc' })}>
      <ProTable<AdminSecurityAPI.SecDept>
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
          return DeptService.page(params);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setDeptFormData({ visiable: true, data: null, parent: null });
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
                    DeptService.deleteBatch(selectedRows).then((response) => {
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

      {deptFormData.visiable ? (
        <SecurityDeptForm
          visible={deptFormData.visiable}
          data={deptFormData.data}
          parent={deptFormData.parent}
          onCancel={() => {
            setDeptFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setDeptFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      ) : null}


    </PageContainer>
  );
}

export default AdminSecurityDeptWeb;

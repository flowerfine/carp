import React, {useRef, useState} from 'react';
import {Button, message, Modal, Space, Table, Tag, Tooltip} from "antd";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ActionType, PageContainer, ProColumns, ProFormInstance, ProTable} from "@ant-design/pro-components";
import {useAccess, useIntl} from "@umijs/max";
import {AdminSecurityAPI} from "@/services/admin/security/typings";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";
import {RoleService} from "@/services/admin/security/role.service";
import SecurityRoleForm from './components/SecurityRoleForm';

export type SecurityRoleState = {
  visiable: boolean;
  data?: AdminSecurityAPI.SecRole | null;
}

const AdminSecurityRoleWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<AdminSecurityAPI.SecRole[]>([]);
  const [roleFormData, setRoleFormData] = useState<SecurityRoleState>({ visiable: false, data: null });

  const columns: ProColumns<AdminSecurityAPI.SecRole>[] = [
    {
      title: intl.formatMessage({ id: 'pages.admin.security.role.name' }),
      dataIndex: 'name',
      width: 200,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.role.code' }),
      dataIndex: 'code',
      hideInSearch: true,
      width: 200,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.role.type' }),
      dataIndex: 'type',
      render: (dom, entity) => {
        return <Tag>{entity.type?.label}</Tag>;
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpSecRoleType)
      },
      width: 200,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.role.status' }),
      dataIndex: 'status',
      render: (dom, entity) => {
        return <Tag>{entity.status?.label}</Tag>;
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpSecRoleStatus)
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
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined />}
              disabled={record.type?.value == '01'}
              onClick={() => {
                setRoleFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.delete.label' })}>
            <Button
              shape="default"
              type="link"
              danger
              icon={<DeleteOutlined />}
              disabled={record.type?.value == '01'}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({ id: 'app.common.operate.delete.confirm.title' }),
                  content: intl.formatMessage({ id: 'app.common.operate.delete.confirm.content' }),
                  okText: intl.formatMessage({ id: 'app.common.operate.confirm.label' }),
                  okButtonProps: { danger: true },
                  cancelText: intl.formatMessage({ id: 'app.common.operate.cancel.label' }),
                  onOk() {
                    RoleService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({ id: 'menu.admin.security.role.desc' })}>
      <ProTable<AdminSecurityAPI.SecRole>
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
          return RoleService.page(params);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setRoleFormData({ visiable: true, data: null });
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
                    RoleService.deleteBatch(selectedRows).then((response) => {
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

      {roleFormData.visiable ? (
        <SecurityRoleForm
          visible={roleFormData.visiable}
          data={roleFormData.data}
          onCancel={() => {
            setRoleFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setRoleFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      ) : null}

    </PageContainer>
  );
}

export default AdminSecurityRoleWeb;

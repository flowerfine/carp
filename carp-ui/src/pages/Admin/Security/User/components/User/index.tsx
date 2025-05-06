import React, { useRef, useState } from 'react';
import { Button, Card, message, Modal, Skeleton, Space, Table, Tag, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, FormOutlined } from "@ant-design/icons";
import { ActionType, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { useAccess, useIntl } from "@umijs/max";
import { AdminSecurityAPI } from "@/services/admin/security/typings";
import { DictService } from "@/services/admin/system/dict.service";
import { DICT_TYPE } from "@/constants/dictType";
import { UserService } from '@/services/admin/security/user.service';
import SecurityUserForm from './SecurityUserForm';
import SecurityRoles2UserForm from "@/pages/Admin/Security/User/components/User/SecurityRoles2UserForm";

export type SecurityUserState = {
  visiable: boolean;
  data?: AdminSecurityAPI.SecUser | null;
}

const AdminSecurityUserRightWeb: React.FC = (props: {
  dept: AdminSecurityAPI.SecDept | null
}) => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<AdminSecurityAPI.SecUser[]>([]);
  const [userFormData, setUserFormData] = useState<SecurityUserState>({ visiable: false, data: null });
  const [roles2UserFormData, setRoles2UserFormData] = useState<SecurityUserState>({visiable: false, data: null});
  const { dept } = props;

  const columns: ProColumns<AdminSecurityAPI.SecUser>[] = [
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.type' }),
      dataIndex: 'type',
      render: (dom, entity) => {
        return (<Tag>{entity.type?.label}</Tag>)
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpSecUserType)
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.userName' }),
      dataIndex: 'userName',
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.nickName' }),
      dataIndex: 'nickName',
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.email' }),
      dataIndex: 'email',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.phone' }),
      dataIndex: 'phone',
      hideInSearch: true
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.user.status' }),
      dataIndex: 'status',
      render: (text, record, index) => {
        return (<Tag>{record.status?.label}</Tag>)
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpSecUserStatus)
      }
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
      title: intl.formatMessage({ id: 'app.common.operate.security.label' }),
      dataIndex: 'actions',
      valueType: 'option',
      align: 'center',
      width: 120,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Tooltip title={intl.formatMessage({ id: 'pages.admin.security.user.roles2user' })}>
            <Button
              shape="default"
              type="link"
              icon={<FormOutlined/>}
              disabled={record.type?.value == '0' || record.status?.value == '2'}
              onClick={() => {
                setRoles2UserFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'pages.admin.security.user.resource.webs2user' })}>
            <Button
              shape="default"
              type="link"
              icon={<FormOutlined />}
              disabled={record.type?.value == '0' || record.status?.value == '2'}
              onClick={() => {
                setRoles2UserFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
        </Space>
      ),
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
              disabled={record.type?.value == '0' || record.status?.value == '2'}
              onClick={() => {
                setUserFormData({ visiable: true, data: record });
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.delete.label' })}>
            <Button
              shape="default"
              type="link"
              danger
              icon={<DeleteOutlined />}
              disabled={record.type?.value == '0' || record.status?.value == '2'}
              onClick={() => {
                Modal.confirm({
                  title: intl.formatMessage({ id: 'app.common.operate.delete.confirm.title' }),
                  content: intl.formatMessage({ id: 'app.common.operate.delete.confirm.content' }),
                  okText: intl.formatMessage({ id: 'app.common.operate.confirm.label' }),
                  okButtonProps: { danger: true },
                  cancelText: intl.formatMessage({ id: 'app.common.operate.cancel.label' }),
                  onOk() {
                    UserService.delete(record).then((response) => {
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
    <>
      {!dept ? (
        <Card style={{ height: 'calc(100vh - 200px)' }} bordered={false}>
          <Skeleton paragraph={{ rows: 10 }} active={true} />
        </Card>
      ) : (
        <ProTable<AdminSecurityAPI.SecUser>
          search={{
            labelWidth: 'auto',
            span: { xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4 },
          }}
          scroll={{ x: 1200 }}
          headerTitle={<Tooltip title={dept?.name}>{dept?.name}</Tooltip>}
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
          params={{ deptId: dept?.id }}
          request={(params, sorter, filter) => {
            return UserService.page(params);
          }}
          toolbar={{
            actions: [
              <Button
                key="new"
                type="primary"
                onClick={() => {
                  setUserFormData({ visiable: true, data: null });
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
                      UserService.deleteBatch(selectedRows).then((response) => {
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
      )}

      {userFormData.visiable ? (
        <SecurityUserForm
          visible={userFormData.visiable}
          data={userFormData.data}
          onCancel={() => {
            setUserFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setUserFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      ) : null}
      {roles2UserFormData.visiable ? (
        <SecurityRoles2UserForm
          visible={roles2UserFormData.visiable}
          data={roles2UserFormData.data}
          onCancel={() => {
            setRoles2UserFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setRoles2UserFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      ) : null}
    </>
  );
}

export default AdminSecurityUserRightWeb;

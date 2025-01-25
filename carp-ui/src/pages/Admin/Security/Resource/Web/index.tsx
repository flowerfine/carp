import React, { useRef, useState } from 'react';
import { Button, message, Modal, Space, Table, Tag, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, PlusOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { history, useAccess, useIntl } from "@umijs/max";
import { AdminSecurityAPI } from "@/services/admin/security/typings";
import { ResourceWebService } from '@/services/admin/security/resourceWeb.service';
import SecurityResourceWebForm from './components/SecurityResourceWebForm';
import {isEmpty} from 'lodash';

export type SecurityResourceWebState = {
  visiable: boolean;
  data?: AdminSecurityAPI.SecResourceWeb | null;
  parent?: AdminSecurityAPI.SecResourceWeb | null;
}

const AdminSecurityResourceWebWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<AdminSecurityAPI.SecResourceWeb[]>([]);
  const [resourceWebFormData, setResourceWebFormData] = useState<SecurityResourceWebState>({ visiable: false, data: null, parent: null });

  const columns: ProColumns<AdminSecurityAPI.SecResourceWeb>[] = [
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.type' }),
      dataIndex: 'type',
      render: (dom, entity) => {
        return <Tag>{entity.type?.label}</Tag>;
      },
      fixed: 'left',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.value' }),
      dataIndex: 'value',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.label' }),
      dataIndex: 'label',
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.path' }),
      dataIndex: 'path',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.order' }),
      dataIndex: 'order',
      hideInSearch: true,
    },
    {
      title: intl.formatMessage({ id: 'pages.admin.security.resource.web.status' }),
      dataIndex: 'status',
      width: 120,
      render: (dom, entity) => {
        return <Tag>{entity.status?.label}</Tag>;
      },
      hideInSearch: true,
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
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.new.label'})}>
                <Button
                  shape="default"
                  type="link"
                  icon={<PlusOutlined/>}
                  onClick={() =>
                    setResourceWebFormData({visiable: true, parent: record, data: null})
                  }
                />
              </Tooltip>
          <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
            <Button
              shape="default"
              type="link"
              icon={<EditOutlined />}
              disabled={record.type?.value == '01'}
              onClick={() => {
                setResourceWebFormData({ visiable: true, data: record });
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
                    ResourceWebService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({ id: 'menu.admin.security.resource.web.desc' })}>
      <ProTable<AdminSecurityAPI.SecResourceWeb>
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
          return ResourceWebService.page(params);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setResourceWebFormData({ visiable: true, data: null });
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
                    ResourceWebService.deleteBatch(selectedRows).then((response) => {
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

      {resourceWebFormData.visiable ? (
        <SecurityResourceWebForm
          visible={resourceWebFormData.visiable}
          data={resourceWebFormData.data}
          parent={resourceWebFormData.parent}
          onCancel={() => {
            setResourceWebFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setResourceWebFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      ) : null}


    </PageContainer>
  );
}

export default AdminSecurityResourceWebWeb;

import React, {useRef, useState} from 'react';
import {history, useIntl} from "@umijs/max";
import {Button, message, Modal, Space, Table, Tooltip} from "antd";
import {ActionType, PageContainer, ProColumns, ProTable} from "@ant-design/pro-components";
import {DeleteOutlined, EditOutlined, FileSearchOutlined} from "@ant-design/icons";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";
import {ScheduleGroupService} from "@/services/workspace/schedule/group.service";
import ScheduleGroupForm from "@/pages/Workspace/Schedule/Group/components/ScheduleGroupForm";

export type ScheduleGroupState = {
  visiable: boolean;
  data?: WorkspaceScheduleAPI.ScheduleGroup | null;
}

const WorkspaceScheduleGroup: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const [selectedRows, setSelectedRows] = useState<WorkspaceScheduleAPI.ScheduleGroup[]>([]);
  const [scheduleGroupFormData, setScheduleGroupFormData] = useState<ScheduleGroupState>({visiable: false, data: null});

  const onDetailClick = (record: WorkspaceScheduleAPI.ScheduleGroup) => {
    history.push('/workspace/schedule/config', record);
  };

  const columns: ProColumns<WorkspaceScheduleAPI.ScheduleGroup>[] = [
    {
      title: intl.formatMessage({id: 'pages.workspace.schedule.group.namespace'}),
      dataIndex: 'namespace',
      hideInTable: true,
      hideInSearch: true
    },
    {
      title: intl.formatMessage({id: 'pages.workspace.schedule.group.name'}),
      dataIndex: 'name',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
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
                setScheduleGroupFormData({visiable: true, data: record});
              }}
            />
          </Tooltip>
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.more.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined/>}
              onClick={() => onDetailClick(record)}
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
                    ScheduleGroupService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({id: 'menu.workspace.schedule.group.desc'})}>
      <ProTable<WorkspaceScheduleAPI.ScheduleGroup>
        search={{
          labelWidth: 'auto',
          span: {xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4},
        }}
        rowKey="id"
        actionRef={actionRef}
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
          return ScheduleGroupService.page(params);
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              onClick={() => {
                setScheduleGroupFormData({visiable: true, data: null});
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
                    ScheduleGroupService.deleteBatch(selectedRows).then((response) => {
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

      {scheduleGroupFormData.visiable && (
        <ScheduleGroupForm
          visible={scheduleGroupFormData.visiable}
          data={scheduleGroupFormData.data}
          onCancel={() => {
            setScheduleGroupFormData({visiable: false, data: null});
          }}
          onFinish={(values) => {
            setScheduleGroupFormData({visiable: false, data: null});
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  );
}

export default WorkspaceScheduleGroup;

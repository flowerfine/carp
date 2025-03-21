import React, { useEffect, useRef, useState } from "react";
import { Button, message, Modal, Space, Table, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { history, useIntl, useLocation } from "@umijs/max";
import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import { ScheduleConfigService } from "@/services/workspace/schedule/config.service";
import { ScheduleGroupService } from "@/services/workspace/schedule/group.service";
import ScheduleConfigForm from "./components/ScheduleConfigForm";
import { DictService } from "@/services/admin/system/dict.service";
import { DICT_TYPE } from "@/constants/dictType";

export type ScheduleConfigState = {
  visiable: boolean;
  data?: WorkspaceScheduleAPI.ScheduleConfig | null;
}

const WorkspaceScheduleConfig: React.FC = () => {
  const intl = useIntl();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();

  const urlParams = useLocation();
  const scheduleGroup = urlParams.state as WorkspaceScheduleAPI.ScheduleGroup;

  const [selectedRows, setSelectedRows] = useState<WorkspaceScheduleAPI.ScheduleConfig[]>([]);
  const [scheduleConfigFormData, setScheduleConfigFormData] = useState<ScheduleConfigState>({ visiable: false, data: null });

  const [scheduleGroups, setScheduleGroups] = useState<WorkspaceScheduleAPI.ScheduleGroup[]>();
  const [jobGroupId, setJobGroupId] = useState<number>();

  useEffect(() => {
    if (scheduleGroup) {
      onScheduleGroupChange(scheduleGroup)
    } else if (scheduleGroups && scheduleGroups.length > 0) {
      onScheduleGroupChange(scheduleGroups[0])
    }
  }, [scheduleGroup, scheduleGroups]);

  const onScheduleGroupChange = (value: WorkspaceScheduleAPI.ScheduleGroup) => {
    setJobGroupId(value.id)
    formRef.current?.setFieldValue("jobGroupId", value.id)
    formRef.current?.submit()
    actionRef.current?.reload()
  };

  const onDetailClick = (record: WorkspaceScheduleAPI.ScheduleConfig) => {
    history.push('/workspace/schedule/instance', record);
  };

  const columns: ProColumns<WorkspaceScheduleAPI.ScheduleConfig>[] = [
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.jobGroup' }),
      dataIndex: 'jobGroupId',
      hideInTable: true,
      request: (params, props) => {
        return ScheduleGroupService.list().then((result) => {
          if (result.data) {
            setScheduleGroups(result.data)
            return result.data.map((item) => {
              return {
                value: item.id,
                label: item.name
              }
            })
          }
          return []
        })
      },
      fieldProps: (form, config) => {
        return {
          allowClear: false,
          onChange: (value: number) => setJobGroupId(value)
        }
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.type' }),
      dataIndex: 'type',
      render: (dom, record, index) => {
        return record.type.label
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpScheduleType)
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.name' }),
      dataIndex: 'name',
      renderText: (dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{dom}</a>
        </Space>
      ),
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.engineType' }),
      dataIndex: 'engineType',
      render: (dom, record, index) => {
        return record.engineType.label
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpScheduleEngineType)
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.jobType' }),
      dataIndex: 'jobType',
      render: (dom, record, index) => {
        return record.jobType.label
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpScheduleJobType)
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.executeType' }),
      dataIndex: 'executeType',
      render: (dom, record, index) => {
        return record.executeType.label
      },
      request: (params, props) => {
        return DictService.listInstanceByDefinition(DICT_TYPE.carpScheduleExecuteType)
      }
    },
    {
      title: intl.formatMessage({ id: 'pages.workspace.schedule.config.handler' }),
      dataIndex: 'handler',
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
                setScheduleConfigFormData({ visiable: true, data: record });
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
                    ScheduleConfigService.delete(record).then((response) => {
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
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.schedule.config.desc' })}>
      <ProTable<WorkspaceScheduleAPI.ScheduleConfig>
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
          if (jobGroupId) {
            return ScheduleConfigService.page(params);
          }
          return Promise.reject()
        }}
        toolbar={{
          actions: [
            <Button
              key="new"
              type="primary"
              disabled={jobGroupId ? false : true}
              onClick={() => {
                setScheduleConfigFormData({ visiable: true, data: null });
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
                    ScheduleConfigService.deleteBatch(selectedRows).then((response) => {
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

      {scheduleConfigFormData.visiable && (
        <ScheduleConfigForm
          visible={scheduleConfigFormData.visiable}
          data={scheduleConfigFormData.data}
          onCancel={() => {
            setScheduleConfigFormData({ visiable: false, data: null });
          }}
          onFinish={(values) => {
            setScheduleConfigFormData({ visiable: false, data: null });
            actionRef.current?.reload();
          }}
        />
      )}
    </PageContainer>
  )
}

export default WorkspaceScheduleConfig

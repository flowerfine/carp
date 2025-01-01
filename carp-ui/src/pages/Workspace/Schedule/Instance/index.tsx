import { useEffect, useRef, useState } from "react";
import { Button, message, Modal, Space, Switch, Table, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProFormSwitch, ProTable } from "@ant-design/pro-components";
import { useIntl, history, useLocation } from "@umijs/max";
import { DICT_TYPE } from "@/constants/dictType";
import { DictService } from "@/services/admin/system/dict.service";
import { ScheduleInstanceService } from "@/services/workspace/schedule/instance.service";
import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import ScheduleInstanceForm from "./components/ScheduleInstanceForm";

export type ScheduleInstanceState = {
    visiable: boolean;
    jobConfigId: number | null;
    data?: WorkspaceScheduleAPI.ScheduleInstance | null;
}

const WorkspaceScheduleInstance: React.FC = () => {
    const intl = useIntl();
    const actionRef = useRef<ActionType>();
    const formRef = useRef<ProFormInstance>();
    const urlParams = useLocation();
    const scheduleConfig = urlParams.state as WorkspaceScheduleAPI.ScheduleConfig;
    const [selectedRows, setSelectedRows] = useState<WorkspaceScheduleAPI.ScheduleInstance[]>([]);
    const [scheduleInstanceFormData, setScheduleInstanceFormData] = useState<ScheduleInstanceState>({ visiable: false, jobConfigId: null, data: null });

    useEffect(() => {
        if (scheduleConfig) {
            formRef.current?.setFieldValue("jobGroupId", scheduleConfig.id)
            formRef.current?.submit()
            setScheduleInstanceFormData({ visiable: false, jobConfigId: scheduleConfig.id, data: null })
        }
    }, []);

    const onDetailClick = (record: WorkspaceScheduleAPI.ScheduleInstance) => {
        // history.push('/workspace/schedule/config', record);
    };

    const onScheduleSwith = (value: boolean, record: WorkspaceScheduleAPI.ScheduleInstance) => {
        if (value) {
            ScheduleInstanceService.schedule(record.id).then((response) => {
                if (response.success) {
                    message.success(intl.formatMessage({ id: 'app.common.operate.enable.success' }));
                    actionRef.current?.reload();
                }
            });
        } else {
            ScheduleInstanceService.unschedule(record.id).then((response) => {
                if (response.success) {
                    message.success(intl.formatMessage({ id: 'app.common.operate.disable.success' }));
                    actionRef.current?.reload();
                }
            });
        }
    };

    const columns: ProColumns<WorkspaceScheduleAPI.ScheduleInstance>[] = [
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.jobConfigId' }),
            dataIndex: 'jobConfig',
            hideInSearch: true,
            renderText: (dom, record) => record.jobConfig?.name
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.name' }),
            dataIndex: 'name',
            renderText: (dom, record) => (
                <Space>
                    <a onClick={() => onDetailClick(record)}>{dom}</a>
                </Space>
            ),
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.status' }),
            dataIndex: 'status',
            renderText: (dom, record) => (
                <Switch
                    defaultChecked={record.status?.value == '0' ? false : true}
                    onChange={(value) => { onScheduleSwith(value, record) }}
                />
            ),
            request: (params, props) => {
                return DictService.listInstanceByDefinition(DICT_TYPE.carpScheduleStatus)
            }
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.timezone' }),
            dataIndex: 'timezone',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.cron' }),
            dataIndex: 'cron',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.startTime' }),
            dataIndex: 'startTime',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.endTime' }),
            dataIndex: 'endTime',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.timeout' }),
            dataIndex: 'timeout',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.schedule.instance.params' }),
            dataIndex: 'params',
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
                    <Tooltip title={intl.formatMessage({ id: 'app.common.operate.edit.label' })}>
                        <Button
                            shape="default"
                            type="link"
                            icon={<EditOutlined />}
                            onClick={() => {
                                setScheduleInstanceFormData({ visiable: true, jobConfigId: scheduleConfig.id, data: record });
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
                                        ScheduleInstanceService.delete(record).then((response) => {
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
        <PageContainer
          title={intl.formatMessage({ id: 'menu.workspace.schedule.instance' })}
          content={intl.formatMessage({ id: 'menu.workspace.schedule.instance.desc' })}
          onBack={() => history.back()}
        >
            <ProTable<WorkspaceScheduleAPI.ScheduleInstance>
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
                    return ScheduleInstanceService.page({ ...params, jobConfigId: scheduleConfig.id });
                }}
                toolbar={{
                    actions: [
                        <Button
                            key="new"
                            type="primary"
                            onClick={() => {
                                setScheduleInstanceFormData({ visiable: true, jobConfigId: scheduleConfig.id, data: null });
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
                                        ScheduleInstanceService.deleteBatch(selectedRows).then((response) => {
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

            {scheduleInstanceFormData.visiable && (
                <ScheduleInstanceForm
                    visible={scheduleInstanceFormData.visiable}
                    data={scheduleInstanceFormData}
                    onCancel={() => {
                        setScheduleInstanceFormData({ visiable: false, jobConfigId: scheduleConfig.id, data: null });
                    }}
                    onFinish={(values) => {
                        setScheduleInstanceFormData({ visiable: false, jobConfigId: scheduleConfig.id, data: null });
                        actionRef.current?.reload();
                    }}
                />
            )}
        </PageContainer>
    )
}

export default WorkspaceScheduleInstance

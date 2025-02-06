import { useEffect, useRef, useState } from "react";
import { Button, message, Modal, Space, Switch, Table, Tag, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined, FileSearchOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProFormSwitch, ProTable } from "@ant-design/pro-components";
import { useIntl, history, useLocation } from "@umijs/max";
import { DICT_TYPE } from "@/constants/dictType";
import { DictService } from "@/services/admin/system/dict.service";
import { ScheduleInstanceService } from "@/services/workspace/schedule/instance.service";
import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import { WorkspaceOrcaAPI } from "@/services/workspace/orca/typings";
import { OrcaPipelineExecutionService } from "@/services/workspace/orca/pipeline.service";

export type ScheduleOrcaState = {
    visiable: boolean;
    data?: WorkspaceOrcaAPI.OrcaPipelineExecution | null;
}

const WorkspaceOrcaInstance: React.FC = () => {
    const intl = useIntl();
    const actionRef = useRef<ActionType>();
    const formRef = useRef<ProFormInstance>();

    const [selectedRows, setSelectedRows] = useState<WorkspaceOrcaAPI.OrcaPipelineExecution[]>([]);

    const onDetailClick = (record: WorkspaceOrcaAPI.OrcaPipelineExecution) => {
        history.push('/workspace/orca/detail', record);
    };

    const columns: ProColumns<WorkspaceOrcaAPI.OrcaPipelineExecution>[] = [
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.namespace' }),
            dataIndex: 'namespace',
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.uuid' }),
            dataIndex: 'uuid',
            hideInSearch: true
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.name' }),
            dataIndex: 'name',
            renderText: (dom, record) => (
                <Space>
                    <a onClick={() => onDetailClick(record)}>{dom}</a>
                </Space>
            ),
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.type' }),
            dataIndex: 'type',
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.status' }),
            dataIndex: 'status'
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.buildTime' }),
            dataIndex: 'buildTime',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.startTime' }),
            dataIndex: 'startTime',
            hideInSearch: true,
        },
        {
            title: intl.formatMessage({ id: 'pages.workspace.orca.instance.endTime' }),
            dataIndex: 'endTime',
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
            title={intl.formatMessage({ id: 'menu.workspace.orca.instance' })}
            content={intl.formatMessage({ id: 'menu.workspace.orca.instance.desc' })}
        >
            <ProTable<WorkspaceOrcaAPI.OrcaPipelineExecution>
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
                    return OrcaPipelineExecutionService.list().then(res => {
                        const result = {
                            data: res.data,
                            total: res.data?.length,
                            pageSize: res.data?.length,
                            current: 1
                        };
                        return result;
                    })
                }}
                toolbar={{
                    actions: [
                        <Button
                            key="new"
                            type="primary"
                            onClick={() => {
                                // setScheduleInstanceFormData({ visiable: true, jobConfigId: scheduleConfig.id, data: null });
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

        </PageContainer>
    )
}

export default WorkspaceOrcaInstance

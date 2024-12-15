import React, {useRef, useState} from 'react';
import {history, useAccess, useIntl} from "@umijs/max";
import {Button, Space, Table, Tooltip} from "antd";
import {ActionType, PageContainer, ProColumns, ProTable} from "@ant-design/pro-components";
import {FileSearchOutlined} from "@ant-design/icons";
import {MetalakeService} from "@/services/metadata/gravitino/metalake.service";

const MetadataGravitinoMetalakeWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const [selectedRows, setSelectedRows] = useState<MetadataGravitinoAPI.Metalake[]>([]);

  const onDetailClick = (record: API.RuleListItem) => {
    history.push('/metadata/gravitino/metalake/catalog', record);
  };

  const columns: ProColumns<API.RuleListItem>[] = [
    {
      title: intl.formatMessage({id: 'pages.metadata.gravitino.metalake.name'}),
      dataIndex: 'name',
      renderText: (_dom, record) => (
        <Space>
          <a onClick={() => onDetailClick(record)}>{record.name}</a>
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
          <Tooltip title={intl.formatMessage({id: 'app.common.operate.more.label'})}>
            <Button
              shape="default"
              type="link"
              icon={<FileSearchOutlined/>}
              onClick={() => onDetailClick(record)}
            />
          </Tooltip>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer content={intl.formatMessage({id: 'pages.metadata.gravitino.metalake.description'})}>
      <ProTable<MetadataGravitinoAPI.Metalake>
        search={{
          labelWidth: 'auto',
          span: {xs: 24, sm: 12, md: 8, lg: 6, xl: 6, xxl: 4},
        }}
        rowKey="name"
        actionRef={actionRef}
        options={false}
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
          return MetalakeService.page(params);
        }}
      />
    </PageContainer>
  );
}

export default MetadataGravitinoMetalakeWeb;

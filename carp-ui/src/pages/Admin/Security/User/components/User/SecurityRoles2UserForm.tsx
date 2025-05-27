import React, {useCallback, useEffect, useMemo, useState} from 'react';
import {Button, Flex, message, Modal, TableColumnsType, Tag} from 'antd';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {AdminSecurityAPI} from '@/services/admin/security/typings';
import TableTransfer, {TransferDataType} from "@/components/TableTransfer";
import {AuthorizationService} from '@/services/admin/security/authorization.service';

export default (props: ModalFormProps<AdminSecurityAPI.SecUser>) => {
  const intl = useIntl();
  const {visible, data, onCancel, onFinish} = props;
  const [roleLists, setRoleLists] = useState<TransferDataType[]>([]);

  // 异步获取数据
  const fetchData = useCallback(async () => {
    try {
      const unauthorized = await AuthorizationService.listUnauthorizedRolesByUserId({userId: data?.id})
        .then(response => {
          return response.data?.records?.map(role => {
            const dataType: TransferDataType = {
              id: role.id,
              name: role.name,
              type: role.type,
              status: role.status,
              remark: role.remark
            }
            return dataType;
          })
        });
      const authorized = await AuthorizationService.listAuthorizedRolesByUserId({userId: data?.id})
        .then(response => {
          return response.data?.records?.map(role => {
            const dataType: TransferDataType = {
              id: role.id,
              name: role.name,
              type: role.type,
              status: role.status,
              remark: role.remark
            }
            return dataType;
          })
        });
      if (unauthorized && authorized) {
        const mergedArray = mergeArrays(unauthorized, authorized);
        setRoleLists(mergedArray);
      }
    } catch (error) {
      console.error(error);
    }
  }, [data]);

  useEffect(() => {
    if (data) {
      fetchData();
    }
  }, [data, fetchData]);

  // 获取选中角色的 id 数组
  const originTargetKeys = useMemo(() => {
    return roleLists?.filter((item) => item.checkOut === 1).map((item) => item.id);
  }, [roleLists]);

  // 角色转移事件处理
  const handleChange = useCallback(
    async (targetKeys, direction, moveKeys) => {
      const roleIds = moveKeys.map((item: string | number) => +item);
      const params = {
        userId: data?.id,
        roleIds: roleIds,
      };
      if (direction === 'right') {
        // 批量为角色绑定用户
        await AuthorizationService.authorizeUser2Roles(params).then((res) => {
          if (res?.success) {
            message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}), 2);
          }
        });
      } else {
        // 批量为角色解除用户绑定
        await AuthorizationService.unauthorizeUser2Roles(params).then((res) => {
          message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}), 2);
        });
      }
      fetchData();
    },
    [data, fetchData, intl],
  );

  // 过滤方法
  const handleFilter = useCallback((inputValue, item) => {
    return item?.name.indexOf(inputValue) !== -1;
  }, []);

  // 合并数组
  function mergeArrays(unauthorized: TransferDataType[], authorized: TransferDataType[]): any {
    unauthorized.forEach((obj, index: any) => {
      obj.checkOut = 0;
    });
    authorized.forEach((obj, index: any) => {
      obj.checkOut = 1;
    });
    return [...unauthorized, ...authorized];
  }

  const tableColumns: TableColumnsType<TransferDataType> = [
    {
      dataIndex: 'name',
      title: intl.formatMessage({id: 'pages.admin.security.role'}),
      width: 300,
    },
    {
      title: intl.formatMessage({id: 'pages.admin.security.role.type'}),
      dataIndex: 'type',
      render: (dom, entity) => {
        return <Tag>{entity.type?.label}</Tag>;
      },
      width: 200,
    },
    {
      title: intl.formatMessage({id: 'pages.admin.security.role.status'}),
      dataIndex: 'status',
      render: (dom, entity) => {
        return <Tag>{entity.status?.label}</Tag>;
      },
      width: 200,
    },
    {
      title: intl.formatMessage({id: 'app.common.data.remark'}),
      dataIndex: 'remark',
      width: 300,
    },
  ];

  return (

    <Modal
      open={visible}
      title={intl.formatMessage({id: 'pages.admin.security.user.roles2user'})}
      width={1100}
      centered
      destroyOnClose={true}
      onCancel={onCancel}
      cancelText={intl.formatMessage({id: 'app.common.operate.close.label'})}
      closeIcon={false}
      footer={[
        <Button type="primary" onClick={onCancel}>
          {intl.formatMessage({id: 'app.common.operate.close.label'})}
        </Button>,
      ]}
    >
      <Flex align="start" gap="middle" vertical>
        <TableTransfer
          dataSource={roleLists}
          targetKeys={originTargetKeys}
          titles={[intl.formatMessage({id: 'pages.admin.security.authorization.user2Roles.unauthorized'}), intl.formatMessage({id: 'pages.admin.security.authorization.user2Roles.authorized'})]}
          showSearch
          rowKey={(record: { id: any }) => record.id}
          showSelectAll={false}
          onChange={handleChange}
          filterOption={handleFilter}
          leftColumns={tableColumns}
          rightColumns={tableColumns}
        />
      </Flex>
    </Modal>
  );
};

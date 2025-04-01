import React, { useRef, useState } from 'react';
import { Button, Col, message, Modal, Row, Space, Table, Tag, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProFormInstance, ProTable } from "@ant-design/pro-components";
import { history, useAccess, useIntl } from "@umijs/max";
import { AdminSecurityAPI } from "@/services/admin/security/typings";
import { DictService } from "@/services/admin/system/dict.service";
import { DICT_TYPE } from "@/constants/dictType";
import { UserService } from '@/services/admin/security/user.service';
import SecurityUserForm from './components/User/SecurityUserForm';
import AdminSecurityUserRightWeb from './components/User';
import AdminSecurityUserLeftDeptWeb from './components/Dept';

export type SecurityUserState = {
  visiable: boolean;
  data?: AdminSecurityAPI.SecUser | null;
}

const AdminSecurityUserWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();
  const [selectedRows, setSelectedRows] = useState<AdminSecurityAPI.SecUser[]>([]);
  const [userFormData, setUserFormData] = useState<SecurityUserState>({ visiable: false, data: null });

  const [dept, setDept] = useState<AdminSecurityAPI.SecDept>(null);

  const treeOnSelect = (data: AdminSecurityAPI.SecDept) => {
    setDept(data);
  };

  /**左侧布局*/
  const leftLayout = { xxl: 5, lg: 6, md: 24, sm: 24, xs: 24 };
  /**右侧布局*/
  const rightLayout = { xxl: 19, lg: 18, md: 24, sm: 24, xs: 24 };

  return (
    <PageContainer content={intl.formatMessage({ id: 'menu.admin.security.user.desc' })}>
      <Row gutter={[16, 16]}>
        <Col {...leftLayout} style={{ minHeight: '100%', overflow: 'auto' }}>
          <AdminSecurityUserLeftDeptWeb onSelect={treeOnSelect}/>
        </Col>
        <Col {...rightLayout} style={{ minHeight: '100%', overflow: 'auto' }}>
          <AdminSecurityUserRightWeb dept={dept}/>
        </Col>
      </Row>
    </PageContainer >
  );
}

export default AdminSecurityUserWeb;

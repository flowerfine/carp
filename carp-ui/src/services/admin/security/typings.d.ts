// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace AdminSecurityAPI {
  type AuthCode = {
    uuid: string;
    img: string;
  };
  type LoginInfo = {
    userName: string;
    password: string;
    authCode: string;
    uuid: string;
    autoLogin: boolean;
  };
  type OnlineUserInfo = {
    token: string;
    userId?: number;
    type?: string;
    status?: string;
    userName?: string;
    nickName?: string;
    avatar?: string;
    email?: string;
    phone?: string;
    roles?: Array<SecRole>;
    resourceWebs?: Array<SecResourceWeb>;

    privileges?: string[];
    roles?: string[];
    expireTime?: bigint;
  };

  type SecDept = {
    id?: number;
    code: string;
    name: string;
    pid: number;
    status: Dict;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type SecDeptParam = QueryParam & {
    code?: string;
    name?: string;
    pid?: string;
    status?: string;
  };

  type SecUser = {
    id?: number;
    type?: Dict;
    userName?: string;
    nickName?: string;
    avatar?: string;
    email?: string;
    phone?: string;
    password?: string;
    order?: number;
    status?: Dict;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };
  type SecUserParam = QueryParam & {
    userName?: string;
    nickName?: string;
    email?: string;
    userStatus?: string;
    roleId?: string;
    deptId?: string;
  };

  type SecRole = {
    id: number;
    type: Dict;
    code?: string;
    name: string;
    order?: number;
    status: Dict;
    remark?: string;
    createTime: Date;
    updateTime: Date;
  };
  type SecRoleParam = QueryParam & {
    type?: string;
    name?: string;
    status?: string;
  };

  type SecResourceWeb = {
    id: number;
    type: Dict;
    pid: number;
    value: string;
    label: string;
    path: string;
    order?: number;
    status: Dict;
    remark?: string;
    children?: SecResourceWeb[];
    authorized?: Dict
  };
  type SecResourceWebParam = QueryParam & {
    pid?: number;
    label?: string;
  };
  type SecResourceWebAddParam = {
    type: string;
    pid: number;
    name?: string;
    path?: string;
    redirect?: string;
    layout?: boolean;
    icon?: string;
    component?: string;
    remark?: string;
  };
  type SecResourceWebUpdateParam = {
    id: number;
    type: string;
    pid: number;
    name?: string;
    path?: string;
    redirect?: string;
    layout?: boolean;
    icon?: string;
    component?: string;
    remark?: string;
  };
}

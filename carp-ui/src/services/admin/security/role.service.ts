import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import { AdminSecurityAPI } from './typings';

export const RoleService = {
  url: '/api/carp/security/role',

  page: async (param: AdminSecurityAPI.SecRoleParam) => {
    return request<ResponseBody<PageResponse<AdminSecurityAPI.SecRole>>>(`${RoleService.url}/page`, {
      method: 'GET',
      params: param,
    }).then((res) => {
      const result = {
        data: res.data?.records,
        total: res.data?.total,
        pageSize: res.data?.size,
        current: res.data?.current,
      };
      return result;
    });
  },

  listAllRole: async () => {
    return request<AdminSecurityAPI.SecRole[]>(`${RoleService.url}`, {
      method: 'GET',
    });
  },

  add: async (row: AdminSecurityAPI.SecRole) => {
    return request<ResponseBody<any>>(`${RoleService.url}`, {
      method: 'PUT',
      data: row,
    });
  },
  update: async (row: AdminSecurityAPI.SecRole) => {
    return request<ResponseBody<any>>(`${RoleService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: AdminSecurityAPI.SecRole) => {
    return request<ResponseBody<any>>(`${RoleService.url}/${row.id}`, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: AdminSecurityAPI.SecRole[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${RoleService.url}/batch`, {
      method: 'DELETE',
      data: params
    });
  },

  grantRoleToUsers: async (roleId: string, userIds: string[]) => {
    return request<ResponseBody<any>>(`${RoleService.url}/grant`, {
      method: 'POST',
      data: {roleId: roleId, userIds: JSON.stringify(userIds)},
      headers: {'Content-Type': 'multipart/form-data'},
    });
  },

  listRoleByDept: async (deptId: string) => {
    return request<AdminSecurityAPI.SecRole[]>(`${RoleService.url}/dept`, {
      method: 'GET',
      params: {grant: 1, deptId: deptId},
    });
  },

  listGrantRoleByDept: async (deptId: string) => {
    return request<AdminSecurityAPI.SecRole[]>(`${RoleService.url}/dept`, {
      method: 'GET',
      params: {deptId: deptId},
    });
  },

  grantDeptRole: async (deptId: string, roleId: string) => {
    return request<ResponseBody<any>>(`${RoleService.url}/dept/grant`, {
      method: 'GET',
      params: {deptId: deptId, roleId: roleId},
    });
  },

  revokeDeptRole: async (deptId: string, roleId: string) => {
    return request<ResponseBody<any>>(`${RoleService.url}/dept/revoke`, {
      method: 'GET',
      params: {deptId: deptId, roleId: roleId},
    });
  },
};

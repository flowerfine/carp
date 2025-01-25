import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import { AdminSecurityAPI } from './typings';

export const DeptService = {
  url: '/api/carp/security/dept',

  page: async (param: AdminSecurityAPI.SecDeptParam) => {
    return request<ResponseBody<PageResponse<AdminSecurityAPI.SecDept>>>(`${DeptService.url}/page`, {
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

  listAll: async (param: AdminSecurityAPI.SecDeptParam) => {
    return request<ResponseBody<AdminSecurityAPI.SecDept[]>>(`${DeptService.url}`, {
      method: 'GET',
    });
  },

  add: async (row: AdminSecurityAPI.SecDept) => {
    return request<ResponseBody<any>>(`${DeptService.url}`, {
      method: 'PUT',
      data: row,
    });
  },
  update: async (row: AdminSecurityAPI.SecDept) => {
    return request<ResponseBody<any>>(`${DeptService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: AdminSecurityAPI.SecDept) => {
    return request<ResponseBody<any>>(`${DeptService.url}/${row.id}`, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: AdminSecurityAPI.SecDept[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${DeptService.url}/batch`, {
      method: 'DELETE',
      data: params
    });
  },
};

import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import { AdminSecurityAPI } from './typings';

export const ResourceWebService = {
  url: '/api/carp/security/resource/web',

  page: async (param: AdminSecurityAPI.SecResourceWebParam) => {
    return request<ResponseBody<PageResponse<AdminSecurityAPI.SecResourceWeb>>>(`${ResourceWebService.url}/page`, {
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

  listAll: async (param: AdminSecurityAPI.SecResourceWebParam) => {
    return request<ResponseBody<AdminSecurityAPI.SecResourceWeb[]>>(`${ResourceWebService.url}`, {
      method: 'GET',
      params: param,
    });
  },

  add: async (row: AdminSecurityAPI.SecResourceWebAddParam) => {
    return request<ResponseBody<any>>(`${ResourceWebService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: AdminSecurityAPI.SecResourceWebUpdateParam) => {
    return request<ResponseBody<any>>(`${ResourceWebService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: AdminSecurityAPI.SecResourceWeb) => {
    return request<ResponseBody<any>>(`${ResourceWebService.url}/${row.id}`, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: AdminSecurityAPI.SecResourceWeb[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${ResourceWebService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};

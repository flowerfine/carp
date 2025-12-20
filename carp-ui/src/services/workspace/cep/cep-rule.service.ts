import { PageResponse, ResponseBody } from '@/typings';
import { request } from '@umijs/max';
import { WorkspaceCepAPI } from './typings';

export const CepRuleService = {
  url: '/api/carp/cep/rule',

  page: async (queryParam: WorkspaceCepAPI.CepRulePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceCepAPI.CepRule>>>(
      `${CepRuleService.url}/page`,
      {
        method: 'GET',
        params: queryParam,
      },
    ).then((res) => {
      const result = {
        data: res.data?.records,
        total: res.data?.total,
        pageSize: res.data?.size,
        current: res.data?.current,
      };
      return result;
    });
  },

  add: async (row: WorkspaceCepAPI.CepRuleAddParam) => {
    return request<ResponseBody<any>>(`${CepRuleService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceCepAPI.CepRuleUpdateParam) => {
    return request<ResponseBody<any>>(`${CepRuleService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceCepAPI.CepRule) => {
    return request<ResponseBody<any>>(`${CepRuleService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceCepAPI.CepRule[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${CepRuleService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};

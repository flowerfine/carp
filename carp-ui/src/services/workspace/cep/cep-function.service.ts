import { PageResponse, ResponseBody } from '@/typings';
import { request } from '@umijs/max';
import { WorkspaceCepAPI } from './typings';

export const CepFunctionService = {
  url: '/api/carp/cep/function',

  page: async (param: WorkspaceCepAPI.CepFunctionPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceCepAPI.CepFunction>>>(`${CepFunctionService.url}/page`, {
      method: 'GET',
      params: param
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

};

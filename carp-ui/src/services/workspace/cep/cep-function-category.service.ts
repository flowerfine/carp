import { ResponseBody } from '@/typings';
import { request } from '@umijs/max';
import { WorkspaceCepAPI } from './typings';

export const CepFunctionCategoryService = {
  url: '/api/carp/cep/function/category',

  listAll: async (namespace: string) => {
    return request<ResponseBody<Array<WorkspaceCepAPI.CepFunctionCategory>>>(`${CepFunctionCategoryService.url}/listAll`, {
      method: 'GET',
      params: {
        namespace: namespace
      }
    });
  },

};

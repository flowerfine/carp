import { PageResponse, ResponseBody } from '@/typings';
import { request } from '@umijs/max';
import { WorkspaceCepAPI } from './typings';

export const CepWorkflowService = {
  url: '/api/carp/cep/workflow',

  page: async (queryParam: WorkspaceCepAPI.CepWorkflowPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceCepAPI.CepWorkflow>>>(
      `${CepWorkflowService.url}/page`,
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

  get: async (id: number | string) => {
    return request<ResponseBody<WorkspaceCepAPI.CepWorkflow>>(`${CepWorkflowService.url}/${id}`, {
      method: 'GET'
    });
  },

  add: async (row: WorkspaceCepAPI.CepWorkflowAddParam) => {
    return request<ResponseBody<any>>(`${CepWorkflowService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceCepAPI.CepWorkflowUpdateParam) => {
    return request<ResponseBody<any>>(`${CepWorkflowService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceCepAPI.CepWorkflow) => {
    return request<ResponseBody<any>>(`${CepWorkflowService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceCepAPI.CepWorkflow[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${CepWorkflowService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};

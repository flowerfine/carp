import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceWorkflowAPI} from './typings';

export const ServerlessWorkflowInstanceService = {
  url: '/api/carp/serverless-workflow/instance',

  page: async (queryParam: WorkspaceWorkflowAPI.ServerlessWorkflowInstancePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>>>(`${ServerlessWorkflowInstanceService.url}/page`, {
      method: 'GET',
      params: queryParam,
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

  get: async (id: number) => {
    return request<ResponseBody<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>>(`${ServerlessWorkflowInstanceService.url}/${id}`, {
      method: 'GET'
    });
  },

  toX6Graph: async (id: number) => {
    return request<ResponseBody<X6API.Graph>>(`${ServerlessWorkflowInstanceService.url}/${id}/x6graph`, {
      method: 'GET'
    });
  },

  toPlantUML: async (id: number) => {
    return request<ResponseBody<String>>(`${ServerlessWorkflowInstanceService.url}/${id}/plantuml`, {
      method: 'GET'
    });
  },

  toMermaid: async (id: number) => {
    return request<ResponseBody<String>>(`${ServerlessWorkflowInstanceService.url}/${id}/mermaid`, {
      method: 'GET'
    });
  },

  run: async (row: WorkspaceWorkflowAPI.ServerlessWorkflowDefinitionRunParam) => {
    return request<ResponseBody<any>>(`${ServerlessWorkflowInstanceService.url}/run`, {
      method: 'POST',
      data: row,
    });
  },
};

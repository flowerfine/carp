import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceWorkflowAPI} from './typings';
import {ServerlessWorkflowDefinitionGraph} from "@/services/workspace/workflow/typings";

export const ServerlessWorkflowDefinitionService = {
  url: '/api/carp/serverless-workflow/definition',

  page: async (queryParam: WorkspaceWorkflowAPI.ServerlessWorkflowDefinitionPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>>>(`${ServerlessWorkflowDefinitionService.url}/page`, {
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
    return request<ResponseBody<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>>(`${ServerlessWorkflowDefinitionService.url}/${id}`, {
      method: 'GET'
    });
  },

  toX6Graph: async (id: number) => {
    return request<ResponseBody<X6API.Graph>>(`${ServerlessWorkflowDefinitionService.url}/${id}/x6graph`, {
      method: 'GET'
    });
  },

  toPlantUML: async (id: number) => {
    return request<ResponseBody<String>>(`${ServerlessWorkflowDefinitionService.url}/${id}/plantuml`, {
      method: 'GET'
    });
  },

  toMermaid: async (id: number) => {
    return request<ResponseBody<String>>(`${ServerlessWorkflowDefinitionService.url}/${id}/mermaid`, {
      method: 'GET'
    });
  },

  getDnds: async () => {
    return request<ResponseBody<Array<Record<string, any>>>>(`${ServerlessWorkflowDefinitionService.url}/dag/dnd`, {
      method: 'GET',
    });
  },

  updateName: async (row: WorkspaceWorkflowAPI.ServerlessWorkflowDefinitionUpdateNameParam) => {
    return request<ResponseBody<any>>(`${ServerlessWorkflowDefinitionService.url}/updateName`, {
      method: 'POST',
      data: row,
    });
  },

};

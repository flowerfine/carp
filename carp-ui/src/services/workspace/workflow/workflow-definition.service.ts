import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceWorkflowAPI} from './typings';

export const WorkflowDefinitionService = {
  url: '/api/carp/workflow/definition',

  page: async (queryParam: WorkspaceWorkflowAPI.WorkflowDefinitionPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceWorkflowAPI.WorkflowDefinition>>>(`${WorkflowDefinitionService.url}/page`, {
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
    return request<ResponseBody<WorkspaceWorkflowAPI.WorkflowDefinition>>(`${WorkflowDefinitionService.url}/${id}`, {
      method: 'GET'
    });
  },

  getGraph: async (id: number) => {
    return request<ResponseBody<X6API.Graph>>(`${WorkflowDefinitionService.url}/${id}/graph`, {
      method: 'GET'
    });
  },

  getDnds: async () => {
    return request<ResponseBody<Array<Record<string, any>>>>(`${WorkflowDefinitionService.url}/dag/dnd`, {
      method: 'GET',
    });
  },

  updateName: async (row: WorkspaceWorkflowAPI.WorkflowDefinitionUpdateNameParam) => {
    return request<ResponseBody<any>>(`${WorkflowDefinitionService.url}/updateName`, {
      method: 'POST',
      data: row,
    });
  },

};

import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceWorkflowAPI} from './typings';

export const WorkflowInstanceService = {
  url: '/api/carp/workflow/instance',

  page: async (queryParam: WorkspaceWorkflowAPI.WorkflowInstancePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceWorkflowAPI.WorkfflowInstance>>>(`${WorkflowInstanceService.url}/page`, {
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
    return request<ResponseBody<WorkspaceWorkflowAPI.WorkfflowInstance>>(`${WorkflowInstanceService.url}/${id}`, {
      method: 'GET'
    });
  },

  getGraph: async (id: number) => {
    return request<ResponseBody<X6API.Graph>>(`${WorkflowInstanceService.url}/${id}/graph`, {
      method: 'GET'
    });
  },

  run: async (row: WorkspaceWorkflowAPI.WorkflowDefinitionRunParam) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/run`, {
      method: 'POST',
      data: row,
    });
  },
};

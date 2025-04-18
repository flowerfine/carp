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

  list: async (queryParam: WorkspaceWorkflowAPI.WorkflowDefinitionPageParam) => {
    return request<ResponseBody<Array<WorkspaceWorkflowAPI.WorkflowDefinition>>>(`${WorkflowDefinitionService.url}`, {
      method: 'GET',
      params: queryParam,
    });
  },

  get: async (id: number) => {
    return request<ResponseBody<WorkspaceWorkflowAPI.WorkflowDefinition>>(`${WorkflowDefinitionService.url}/${id}`, {
      method: 'GET'
    });
  },

  add: async (row: WorkspaceWorkflowAPI.WorkflowDefinitionAddParam) => {
    return request<ResponseBody<any>>(`${WorkflowDefinitionService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceWorkflowAPI.WorkflowDefinitionUpdateParam) => {
    return request<ResponseBody<any>>(`${WorkflowDefinitionService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceWorkflowAPI.WorkflowDefinition) => {
    return request<ResponseBody<any>>(`${WorkflowDefinitionService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceWorkflowAPI.WorkflowDefinition[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${WorkflowDefinitionService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

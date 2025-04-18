import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceWorkflowAPI} from './typings';

export const WorkflowInstanceService = {
  url: '/api/carp/workflow/instance',

  page: async (queryParam: WorkspaceWorkflowAPI.WorkflowInstancePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceWorkflowAPI.WorkflowInstance>>>(`${WorkflowInstanceService.url}/page`, {
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
    return request<ResponseBody<WorkspaceWorkflowAPI.WorkflowInstance>>(`${WorkflowInstanceService.url}/${id}`, {
      method: 'GET'
    });
  },

  add: async (row: WorkspaceWorkflowAPI.WorkflowInstanceAddParam) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceWorkflowAPI.WorkflowInstanceUpdateParam) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceWorkflowAPI.WorkflowInstance) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceWorkflowAPI.WorkflowInstance[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

  start: async (param: WorkspaceWorkflowAPI.WorkflowInstance) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/start`, {
      method: 'POST',
      data: {id: param.id},
    });
  },

  stop: async (param: WorkspaceWorkflowAPI.WorkflowInstance) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/stop`, {
      method: 'POST',
      data: {id: param.id},
    });
  },

};

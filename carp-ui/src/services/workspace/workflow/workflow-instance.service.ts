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

  add: async (row: WorkspaceWorkflowAPI.ScheduleConfigAddParam) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceWorkflowAPI.ScheduleConfigUpdateParam) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceWorkflowAPI.WorkfflowInstance) => {
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceWorkflowAPI.WorkfflowInstance[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${WorkflowInstanceService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

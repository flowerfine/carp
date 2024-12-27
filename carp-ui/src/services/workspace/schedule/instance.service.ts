import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const ScheduleInstanceService = {
  url: '/api/carp/schedule/instance',

  page: async (queryParam: WorkspaceScheduleAPI.ScheduleInstanceParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleInstance>>>(`${ScheduleInstanceService.url}/page`, {
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

  add: async (row: WorkspaceScheduleAPI.ScheduleInstance) => {
    return request<ResponseBody<any>>(`${ScheduleInstanceService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleInstance) => {
    return request<ResponseBody<any>>(`${ScheduleInstanceService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceScheduleAPI.ScheduleInstance) => {
    return request<ResponseBody<any>>(`${ScheduleInstanceService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleInstance[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${ScheduleInstanceService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

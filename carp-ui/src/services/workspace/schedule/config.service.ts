import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const ScheduleConfigService = {
  url: '/api/carp/schedule/config',

  page: async (queryParam: WorkspaceScheduleAPI.ScheduleConfigPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleConfig>>>(`${ScheduleConfigService.url}/page`, {
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

  add: async (row: WorkspaceScheduleAPI.ScheduleConfigAddParam) => {
    return request<ResponseBody<any>>(`${ScheduleConfigService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleConfigUpdateParam) => {
    return request<ResponseBody<any>>(`${ScheduleConfigService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceScheduleAPI.ScheduleConfig) => {
    return request<ResponseBody<any>>(`${ScheduleConfigService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleConfig[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${ScheduleConfigService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

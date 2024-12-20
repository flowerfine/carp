import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const ScheduleGroupService = {
  url: '/api/carp/schedule/group',

  page: async (queryParam: WorkspaceScheduleAPI.ScheduleGroupPageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleGroup>>>(`${ScheduleGroupService.url}/page`, {
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

  list: async () => {
    return request<ResponseBody<Array<WorkspaceScheduleAPI.ScheduleGroup>>>(`${ScheduleGroupService.url}`, {
      method: 'GET',
    });
  },

  add: async (row: WorkspaceScheduleAPI.ScheduleGroupAddParam) => {
    return request<ResponseBody<any>>(`${ScheduleGroupService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleGroupUpdateParam) => {
    return request<ResponseBody<any>>(`${ScheduleGroupService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceScheduleAPI.ScheduleGroup) => {
    return request<ResponseBody<any>>(`${ScheduleGroupService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleGroup[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${ScheduleGroupService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

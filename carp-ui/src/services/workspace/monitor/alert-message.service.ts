import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceMonitorAPI} from './typings';

export const MonitorAlertMessageService = {
  url: '/api/carp/alert/message',

  page: async (queryParam: WorkspaceMonitorAPI.AlertMessagePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceMonitorAPI.AlertMessage>>>(`${MonitorAlertMessageService.url}/page`, {
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

  delete: async (row: WorkspaceMonitorAPI.AlertMessage) => {
    return request<ResponseBody<any>>(`${MonitorAlertMessageService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceMonitorAPI.AlertMessage[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${MonitorAlertMessageService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

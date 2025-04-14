import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceMonitorAPI} from './typings';

export const MonitorAlertRuleService = {
  url: '/api/carp/alert/rule',

  page: async (queryParam: WorkspaceMonitorAPI.AlertRulePageParam) => {
    return request<ResponseBody<PageResponse<WorkspaceMonitorAPI.AlertRule>>>(`${MonitorAlertRuleService.url}/page`, {
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

  add: async (row: WorkspaceMonitorAPI.AlertRuleAddParam) => {
    return request<ResponseBody<any>>(`${MonitorAlertRuleService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceMonitorAPI.AlertRuleUpdateParam) => {
    return request<ResponseBody<any>>(`${MonitorAlertRuleService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  delete: async (row: WorkspaceMonitorAPI.AlertRule) => {
    return request<ResponseBody<any>>(`${MonitorAlertRuleService.url}/` + row.id, {
      method: 'DELETE',
    });
  },

  deleteBatch: async (rows: WorkspaceMonitorAPI.AlertRule[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<any>>(`${MonitorAlertRuleService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },

};

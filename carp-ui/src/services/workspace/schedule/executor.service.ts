import { Dict, ResponseBody } from '@/typings';
import { request } from '@umijs/max';

export const ScheduleExecutorService = {
  url: '/api/carp/schedule/executor',

  getEngines: async () => {
    return request<ResponseBody<Array<Dict>>>(`${ScheduleExecutorService.url}/engines`, {
      method: 'GET'
    });
  },

  getTypes: async (engine: string) => {
    return request<ResponseBody<Array<Dict>>>(`${ScheduleExecutorService.url}/${engine}/types`, {
      method: 'GET',
    });
  },

  getExecutors: async (engine: string, type: string) => {
    return request<ResponseBody<Array<Dict>>>(`${ScheduleExecutorService.url}/${engine}/${type}/executors`, {
      method: 'GET',
    });
  },

};

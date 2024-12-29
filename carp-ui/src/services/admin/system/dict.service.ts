import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {AdminSystemAPI} from "@/services/admin/system/typings";

export const DictService = {
  url: '/api/carp/system/dict',

  listDefinition: async (param: AdminSystemAPI.SysDictDefinitionParam) => {
    return request<ResponseBody<PageResponse<AdminSystemAPI.SysDictDefinition>>>(`${DictService.url}/definition/page`, {
      method: 'GET',
      params: param,
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

  listAllDefinition: async () => {
    return request<ResponseBody<Array<AdminSystemAPI.SysDictDefinition>>>(`${DictService.url}/definition/all`, {
      method: 'GET',
    });
  },

  listInstance: async (param: AdminSystemAPI.SysDictInstanceParam) => {
    return request<ResponseBody<PageResponse<AdminSystemAPI.SysDictInstance>>>(`${DictService.url}/definition/instance/page`, {
      method: 'GET',
      params: param,
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

  listInstanceByDefinition: async (code: string) => {
    return request<ResponseBody<Array<AdminSystemAPI.SysDictInstance>>>(`${DictService.url}/definition/instance`, {
      method: 'GET',
      params: {dictDefinitionCode: code},
    }).then((res) => {
      if (res.data) {
        return res.data;
      }
      return []
    });
  },
};

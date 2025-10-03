import { PageResponse, QueryParam, ResponseBody } from '@/typings';
import { request } from '@umijs/max';

export const MetalakeService = {
  url: '/api/carp/datasource/gravitino/metalake',

  page: async (param: QueryParam) => {
    return request<ResponseBody<PageResponse<MetadataGravitinoAPI.Metalake>>>(
      `${MetalakeService.url}/page`,
      {
        method: 'GET',
        params: param,
      },
    ).then((res) => {
      const result = {
        data: res.data?.records,
        total: res.data?.total,
        pageSize: res.data?.size,
        current: res.data?.current,
      };
      return result;
    });
  },

  listCatalogs: async (metalake: string) => {
    return request<ResponseBody<Array<MetadataGravitinoAPI.Catalog>>>(
      `${MetalakeService.url}/${metalake}/catalogs`,
      {
        method: 'GET',
      },
    );
  },

  listSchemas: async (metalake: string, catalog: string) => {
    return request<ResponseBody<Array<MetadataGravitinoAPI.Schema>>>(
      `${MetalakeService.url}/${metalake}/catalogs/${catalog}/schemas`,
      {
        method: 'GET',
      },
    );
  },

  listTables: async (metalake: string, catalog: string, schema: string) => {
    return request<ResponseBody<Array<string>>>(
      `${MetalakeService.url}/${metalake}/catalogs/${catalog}/schemas/${schema}/tables`,
      {
        method: 'GET',
      },
    );
  },

  getTable: async (metalake: string, catalog: string, schema: string, table: string) => {
    return request<ResponseBody<MetadataGravitinoAPI.Table>>(
      `${MetalakeService.url}/${metalake}/catalogs/${catalog}/schemas/${schema}/tables/${table}`,
      {
        method: 'GET',
      },
    );
  },
};

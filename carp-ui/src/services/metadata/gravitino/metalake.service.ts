import {PageResponse, QueryParam, ResponseBody} from '@/typings';
import {request} from '@umijs/max';

export const MetalakeService = {
    url: '/api/carp/datasource/gravitino/metalake',

    page: async (param: QueryParam) => {
        return request<ResponseBody<PageResponse<MetadataGravitinoAPI.Metalake>>>(`${MetalakeService.url}/page`, {
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
};

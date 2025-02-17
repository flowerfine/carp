import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import { WorkspaceOrcaAPI } from './typings';

export const OrcaPipelineExecutionService = {
  url: '/api/carp/orca/pipeline',

  list: async () => {
    return request<ResponseBody<Array<WorkspaceOrcaAPI.OrcaPipelineExecution>>>(`${OrcaPipelineExecutionService.url}/list`, {
      method: 'GET',
    });
  },

};

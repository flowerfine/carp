import {ResponseBody} from '@/typings';
import {request} from '@umijs/max';

export const ServerlessWorkflowService = {
  url: '/api/carp/workflow/serverless-workflow',

  getDnds: async () => {
    return request<ResponseBody<Array<X6API.DndGroup>>>(`${ServerlessWorkflowService.url}/dnds`, {
      method: 'GET',
    });
  },

};

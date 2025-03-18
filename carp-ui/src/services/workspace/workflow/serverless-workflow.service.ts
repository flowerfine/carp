import {ResponseBody} from '@/typings';
import {request} from '@umijs/max';

export const ServerlessWorkflowService = {
  url: '/api/carp/workflow/serverless-workflow',

  getDnds: async () => {
    return request<ResponseBody<Array<X6API.DndGroup>>>(`${ServerlessWorkflowService.url}/dnds`, {
      method: 'GET',
    });
  },

  convertToWorkflow: async (graph: X6API.Graph) => {
    return request<ResponseBody<string>>(`${ServerlessWorkflowService.url}/workflow`, {
      method: 'POST',
      data: graph,
    });
  },

  execute: async (param: any, graph: X6API.Graph) => {
    return request<ResponseBody<string>>(`${ServerlessWorkflowService.url}/execute`, {
      method: 'POST',
      data: {param: param, graph: graph},
    });
  },

};

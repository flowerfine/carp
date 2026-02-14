// @ts-ignore
/* eslint-disable */

import { QueryParam } from '@/typings';

declare namespace WorkspaceCepAPI {
  type CepRule = {
    id: number;
    namespace: string;
    name: string;
    uuid: string;
    type: string;
    nodes: string;
    edges: string;
    skipStrategy: string;
    window?: string;
    function: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type CepRulePageParam = QueryParam & {
    namespace?: string;
    name?: string;
  };

  type CepRuleAddParam = {
    namespace: string;
    name: string;
    nodes: string;
    edges: string;
    skipStrategy: string;
    window?: string;
    function: string;
    remark?: string;
  };

  type CepRuleUpdateParam = CepRuleAddParam & {
    id: number;
  };

  type CepWorkflow = {
    id: number;
    namespace: string;
    name: string;
    uuid: string;
    type: string;
    body?: any;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type CepWorkflowPageParam = QueryParam & {
    namespace?: string;
    name?: string;
  };

  type CepWorkflowAddParam = {
    namespace: string;
    name: string;
    body?: any;
    remark?: string;
  };

  type CepWorkflowUpdateParam = CepWorkflowAddParam & {
    id: number;
  };

  type CepFunctionCategory = {
    namespace: string;
    name: string;
    order?: number;
    remark?: string;
  };

  type CepFunction = {
    namespace: string;
    name: string;
    shortName: string;
    uuid?: string;
    remark?: string;
  };

  type CepFunctionPageParam = QueryParam & {
    namespace: string;
    categoryId: number;
  };

}

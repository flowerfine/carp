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
}

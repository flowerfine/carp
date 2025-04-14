// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceMonitorAPI {

  type AlertRule = {
    id: number;
    namespace: string;
    name: string;
    uuid: string;
    level: Dict;
    promql: string;
    waitFor: string;
    summary: string;
    description: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type AlertRulePageParam = QueryParam & {
    namespace: string;
    name?: string;
    uuid?: string;
  };

  type AlertRuleAddParam = {
    namespace: string;
    name: string;
    uuid: string;
    level: string;
    promql: string;
    waitFor: string;
    summary: string;
    description: string;
    remark?: string;
  };

  type AlertRuleUpdateParam = AlertRuleAddParam & {
    id: number;
  };

}

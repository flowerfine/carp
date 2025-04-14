// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceMonitorAPI {

  type AlertRule = {
    id: number;
    namespace: string;
    name: string;
    uuid: string;
    isEnabled: Dict;
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
    isEnabled: string;
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

  type AlertMessage = {
    id: number;
    namespace: string;
    ruleName: string;
    ruleId: string;
    resourceType: string;
    resourceId: string;
    fingerprint: string;
    status: Dict;
    startTime: string;
    endTime?: string;
    count: number;
    summary: string;
    description: string;
    source: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type AlertMessagePageParam = QueryParam & {
    namespace: string;
    ruleId?: string;
    resourceType?: string;
    resourceId?: string;
  };

}

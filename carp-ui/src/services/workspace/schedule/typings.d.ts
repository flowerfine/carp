// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceScheduleAPI {

  type ScheduleGroup = {
    namespace: string;
    name: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type ScheduleGroupPageParam = QueryParam & {
    namespace?: string;
    name?: string;
  };

  type ScheduleGroupAddParam = {
    namespace: string;
    name: string;
    remark?: string;
  };

  type ScheduleGroupUpdateParam = ScheduleGroupAddParam & {
    id: number;
  };
}

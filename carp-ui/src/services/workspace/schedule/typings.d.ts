// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceScheduleAPI {

  type ScheduleGroup = {
    id: number;
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

  type ScheduleConfig = {
    id: number;
    jobGroup: ScheduleGroup;
    type: Dict;
    engineType: Dict;
    jobType: Dict;
    name: string;
    handler: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type ScheduleConfigPageParam = QueryParam & {
    jobGroupId: number;
    type: string;
    engineType: string;
    jobType: string;
    name?: string;
    handler?: string;
  };

  type ScheduleConfigAddParam = {
    jobGroupId: number;
    type: string;
    engineType: string;
    jobType: string;
    name: string;
    handler: string;
  };

  type ScheduleConfigUpdateParam = ScheduleConfigAddParam & {
    id: number;
  };
}

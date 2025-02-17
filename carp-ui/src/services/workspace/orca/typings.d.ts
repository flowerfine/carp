// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceOrcaAPI {

  type OrcaPipelineExecution = {
    id: number;
    uuid: string;
    namespace: string;
    type: Dict;
    name: string;
    remark?: string;
    origin?: string;
    pipelineConfigId?: string;
    status?: Dict;
    buildTime?: Date;
    startTime?: Date;
    endTime?: Date;
    startTimeExpiry?: Date;
    paused?: Record<string, any>;
    canceled?: boolean;
    canceledBy?: string;
    cancellationReason?: string;
    trigger?: Record<string, any>;
    notifications?: Array;
    spelEvaluator?: string;
    templateVariables?: Record<string, any>;
    stages?: Array<OrcaStageExecution>;
    context?: Record<string, any>;
    limitConcurrent?: boolean;
    maxConcurrentExecutions?: boolean;
    keepWaitingPipelines?: boolean;
    createTime?: Date;
    updateTime?: Date;
  };

  type ScheduleGroupPageParam = QueryParam & {
    
  };

  type OrcaStageExecution = {
    id: number;
    uuid: string;
    refId: string;
    type: string;
    name: string;
    pipelineExecution?: OrcaPipelineExecution;
    startTime?: Date;
    endTime?: Date;
    startTimeExpiry?: Date;
    status?: Dict;
    context?: Record<string, any>;
    outputs?: Record<string, any>;
    tasks?: Array<OrcaTaskExecution>;
    syntheticStageOwner?: string;
    parentStageId?: number;
    requisiteStageRefIds?: Set;
    scheduledTime?: Date;
    lastModified?: Record<string, any>;
    additionalMetricTags?: Record<string, any>;

    createTime?: Date;
    updateTime?: Date;
  };

  type OrcaTaskExecution = {
    id: number;
    uuid: string;
    name: string;
    implementingClass: string;
    startTime?: Date;
    endTime?: Date;
    status?: Dict;
    stageStart?: boolean;
    stageEnd?: boolean;
    loopStart?: boolean;
    loopEnd?: boolean;
    taskExceptionDetails?: Record<string, any>;
  };
}

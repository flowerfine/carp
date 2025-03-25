// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceWorkflowAPI {

  type WorkflowDefinition = {
    id: number;
    namespace: string;
    type: string;
    name: string;
    uuid: string;
    graph?: WorkflowDefinitionGraph;
    meta?: Record<string, any>;
    attrs?: Record<string, any>;
    inputOptions?: Array<Record<string, any>>;
    outputOptions?: Array<Record<string, any>>;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowDefinitionPageParam = QueryParam & {
    namespace?: string;
    type?: string;
    name?: string;
    uuid?: string;
  };

  type WorkflowDefinitionUpdateNameParam = {
    id: number;
    name: string;
  };

  type WorkflowDefinitionRunParam = {
    id: number;
    globalVariable?: Record<string, any>;
  };

  type WorkflowDefinitionGraph = {
    preNode?: WorkflowDefinitionGraphNode;
    postNode: WorkflowDefinitionGraphNode;
    nodes: Array<WorkflowDefinitionGraphNode>;
    edges: Array<WorkflowDefinitionGraphEdge>;
  };

  type WorkflowDefinitionGraphNode = {
    id: number;
    workflowDefinitionId: number;
    stepId: string;
    stepName: string;
    inputOptions?: Array<Record<string, any>>;
    outputOptions?: Array<Record<string, any>>;
    positionX: number;
    positionY: number;
    shape: string;
    style: Record<string, any>;
    meta?: Record<string, any>;
    attrs?: Record<string, any>;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowDefinitionGraphEdge = {
    id: number;
    workflowDefinitionId: number;
    linkId: string;
    linkName: string;
    fromStepId: string;
    toStepId: string;
    shape?: string;
    style?: Record<string, any>;
    meta?: Record<string, any>;
    attrs?: Record<string, any>;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowInstance = {
    id: number;
    namespace: string;
    definition: WorkflowDefinition;
    uuid: string;
    body?: Record<string, any>;
    inputs?: Record<string, any>;
    outputs?: Record<string, any>;
    status: string;
    startTime?: Date;
    endTime?: Date;
    graph: WorkflowInstanceGraph;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowInstancePageParam = QueryParam & {
    namespace?: string;
    uuid?: string;
    status?: string;
  };

  type WorkflowInstanceGraph = {
    preTask?: WorkflowStepInstance;
    postTask: WorkflowStepInstance;
    tasks: Array<WorkflowStepInstance>;
    edges: Array<WorkflowDefinitionGraphEdge>;
  };

  type WorkflowStepInstance = {
    id: number;
    namespace: string;
    workflowInstance: WorkflowInstance;
    node: WorkflowDefinitionGraphNode;
    uuid: string;
    body?: Record<string, any>;
    inputs?: Record<string, any>;
    outputs?: Record<string, any>;
    status: string;
    startTime?: Date;
    endTime?: Date;
    context: Record<string, any>;
    createTime?: Date;
    updateTime?: Date;
  };


}

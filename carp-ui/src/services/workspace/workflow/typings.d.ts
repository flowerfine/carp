// @ts-ignore
/* eslint-disable */

import {Dict, QueryParam} from "@/typings";

declare namespace WorkspaceWorkflowAPI {

  type WorkflowDefinition = {
    id: number;
    namespace: string;
    name: string;
    uuid: string;
    engine: Dict;
    body?: Record<string, any>;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowDefinitionPageParam = QueryParam & {
    namespace: string;
    name?: string;
    uuid?: string;
    engine?: string;
  };

  type WorkflowDefinitionAddParam = {
    namespace: string;
    name: string;
    engine: string;
    body?: Record<string, any>;
    remark?: string;
  };

  type WorkflowDefinitionUpdateParam = WorkflowDefinitionAddParam & {
    id: number;
  };

  type WorkflowInstance = {
    id: number;
    namespace: string;
    workflowDefinitionId: number;
    uuid: string;
    params?: Record<string, any>;
    status: Dict;
    trigger?: Record<string, any>;
    schedulerInstanceId?: number;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type WorkflowInstancePageParam = QueryParam & {
    namespace: string;
    workflowDefinitionId?: number;
    uuid?: string;
    status?: string;
  };

  type WorkflowInstanceAddParam = {
    namespace: string;
    workflowDefinitionId: number;
    params?: Record<string, any>;
    trigger?: Record<string, any>;
    remark?: string;
  };

  type WorkflowInstanceUpdateParam = WorkflowInstanceAddParam & {
    id: number;
  };

  type WorkflowInstanceStartParam = {
    id: number;
  };

  type ServerlessWorkflowDefinition = {
    id: number;
    namespace: string;
    type: string;
    name: string;
    uuid: string;
    graph?: ServerlessWorkflowDefinitionGraph;
    meta?: Record<string, any>;
    attrs?: Record<string, any>;
    inputOptions?: Array<Record<string, any>>;
    outputOptions?: Array<Record<string, any>>;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  type ServerlessWorkflowDefinitionPageParam = QueryParam & {
    namespace?: string;
    type?: string;
    name?: string;
    uuid?: string;
  };

  type ServerlessWorkflowDefinitionUpdateNameParam = {
    id: number;
    name: string;
  };

  type ServerlessWorkflowDefinitionRunParam = {
    id: number;
    globalVariable?: Record<string, any>;
  };

  type ServerlessWorkflowDefinitionGraph = {
    preNode?: ServerlessWorkflowDefinitionGraphNode;
    postNode: ServerlessWorkflowDefinitionGraphNode;
    nodes: Array<ServerlessWorkflowDefinitionGraphNode>;
    edges: Array<ServerlessWorkflowDefinitionGraphEdge>;
  };

  type ServerlessWorkflowDefinitionGraphNode = {
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

  type ServerlessWorkflowDefinitionGraphEdge = {
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

  type ServerlessWorkflowInstance = {
    id: number;
    namespace: string;
    definition: ServerlessWorkflowDefinition;
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

  type ServerlessWorkflowInstancePageParam = QueryParam & {
    namespace?: string;
    uuid?: string;
    status?: string;
  };

  type ServerlessWorkflowInstanceGraph = {
    preTask?: ServerlessWorkflowStepInstance;
    postTask: ServerlessWorkflowStepInstance;
    tasks: Array<ServerlessWorkflowStepInstance>;
    edges: Array<ServerlessWorkflowDefinitionGraphEdge>;
  };

  type ServerlessWorkflowStepInstance = {
    id: number;
    namespace: string;
    workflowInstance: ServerlessWorkflowInstance;
    node: ServerlessWorkflowDefinitionGraphNode;
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

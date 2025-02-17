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
}

/*
 * Copyright 2025 coze-dev Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { ConfigEntity, WorkflowJSON } from '@flowgram.ai/free-layout-editor';
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import { CepWorkflowService } from '@/services/workspace/cep/cep-workflow.service';

export enum WorkflowExecStatus {
  DEFAULT = 'default',
  /** in progress */
  EXECUTING = 'executing',
  /** End of execution (there is still an end of execution banner at this time, and the workflow is disabled) */
  DONE = 'done',
}

export type WorkflowPlaygroundProps = {

  /** Current canvas associated workflowId */
  workflowId: number;
}

/**
 * Current process status
 */
export interface WorkflowGlobalState {
  loading: boolean;
  workflowId: number | undefined;
  info: WorkspaceCepAPI.CepWorkflow | undefined;
}

export class WorkflowGlobalStateEntity extends ConfigEntity<WorkflowGlobalState> {
  static type = 'WorkflowGlobalStateEntity';

  getDefaultConfig(): WorkflowGlobalState {
    return {
      loading: true,
      workflowId: undefined,
      info: undefined
    };
  }

  async load(workflowId: number | undefined): Promise<WorkflowJSON | undefined> {
    if (!workflowId) {
      throw new Error("缺少 workflowId");
    }

    const workflowInfo = await this.queryWorkflowDetail(workflowId);

    this.updateConfig({
      workflowId,
      info: workflowInfo,
    });

    const workflowJSON = (
      workflowInfo?.body ? JSON.parse(workflowInfo?.body) : undefined
    ) as WorkflowJSON;
    return workflowJSON;
  }

  protected async queryWorkflowDetail(
    workflowId: number
  ): Promise<WorkspaceCepAPI.CepWorkflow> {
    return CepWorkflowService.get(workflowId).then((res) => res.data);
  }

  /**
   * Reload
   */
  reload = async () => {
    const workflowJson = await this.load(this.config.workflowId);
    return workflowJson;
  };

  get workflowId(): number | undefined {
    return this.config.workflowId;
  }

  /**
   * canvas description information
   */
  get info(): WorkspaceCepAPI.CepWorkflow {
    return this.config.info;
  }

  setInfo(info: Partial<WorkspaceCepAPI.CepWorkflow>) {
    this.config.info = {
      ...this.config.info,
      ...info,
    } as WorkspaceCepAPI.CepWorkflow;
    this.fireChange();
  }

  /**
   * Is the canvas loading?
   */
  get loading(): boolean {
    return this.config.loading;
  }
}
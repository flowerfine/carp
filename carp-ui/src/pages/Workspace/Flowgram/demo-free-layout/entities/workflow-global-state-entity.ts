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

import { ConfigEntity, PlaygroundConfigEntity, WorkflowJSON } from '@flowgram.ai/free-layout-editor';

/**
 * Current process status
 */
export interface WorkflowGlobalState {
    
    loading: boolean;
    workflowId: string;
    info: any;
}

export class WorkflowGlobalStateEntity extends ConfigEntity<WorkflowGlobalState> {
  static type = 'WorkflowGlobalStateEntity';

  getDefaultConfig(): WorkflowGlobalState {
    return {
      loading: true,
      workflowId: '',
      info: {}
    };
  }

  async load(
    workflowId: string,
    spaceId: string,
  ): Promise<WorkflowJSON | undefined> {

    console.log('Loading workflow...', workflowId)
    if (!workflowId) {
      throw new Error("缺少 workflowId");
    }

    const workflowInfo = await this.queryWorkflowDetail(workflowId, spaceId);

    this.updateConfig({
      workflowId,
      info: workflowInfo,
    });

    // Update the readonly status of the canvas
    this.entityManager
      .getEntity<PlaygroundConfigEntity>(PlaygroundConfigEntity)
      ?.updateConfig({
        /** Initially, the preview state = > canvas is not editable */
        readonly: preview,
      });
    return workflowJSON;
  }

  protected async queryWorkflowDetail(
    workflowId: string,
    spaceId: string,
  ): Promise<WorkflowInfo> {
    try {
      const res = await this.queryCollaborationWorkflow(workflowId, spaceId);
      return res;
    } catch (e) {
      reporter.errorEvent({
        eventName: 'query_workflow_detail_fail',
        namespace: 'workflow',
        error: e,
      });
      // When the public space template is deleted, there is a cache for about 5 minutes. Here, you need to back up to prevent the preview of the deleted process from crashing.
      if (spaceId !== PUBLIC_SPACE_ID) {
        throw e;
      }
      return {};
    }
  }

  /**
   * Reload
   */
  reload = async () => {
    const workflowJson = await this.load(
      this.config.playgroundProps.workflowId,
      this.config.playgroundProps.spaceId || '',
    );
    return workflowJson;
  };

  /**
   * canvas description information
   */
  get info(): WorkflowInfo {
    return this.config.info;
  }

  setInfo(info: Partial<WorkflowInfo>) {
    this.config.info = {
      ...this.config.info,
      ...info,
    } as WorkflowInfo;
    this.fireChange();
  }

  /**
   * Is the canvas loading?
   */
  get loading(): boolean {
    return this.config.loading;
  }
}
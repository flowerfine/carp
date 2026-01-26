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

import { bindConfigEntity, bindContributions, FlowDocumentContribution, FlowRendererContribution } from '@flowgram.ai/free-layout-editor';
import { ContainerModule } from 'inversify';
import { FormContribution } from '@flowgram.ai/form-core';
import { WorkflowGlobalStateEntity } from '../Detail/entities';
import { WorkflowPageContribution } from './workflow-page-contribution';
import { WorkflowSaveService } from '../Detail/services';
import { FormAbilityExtensionsFormContribution } from '../form-extensions';

// eslint-disable-next-line @typescript-eslint/naming-convention
export const WorkflowPageContainerModule = new ContainerModule(
  // eslint-disable-next-line max-params
  (bind, _unbind, _isbound, rebind) => {
    bind(WorkflowSaveService).toSelf().inSingletonScope();
    
    bindConfigEntity(bind, WorkflowGlobalStateEntity);
    bindContributions(bind, WorkflowPageContribution, [
      FlowDocumentContribution,
      FlowRendererContribution,
    ]);

     bindContributions(bind, FormAbilityExtensionsFormContribution, [
      FormContribution,
    ]);
  },
);

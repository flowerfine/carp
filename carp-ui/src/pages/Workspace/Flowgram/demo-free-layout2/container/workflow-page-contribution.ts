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

/* eslint-disable @typescript-eslint/no-explicit-any */

import { injectable } from 'inversify';
import { FlowDocumentContribution, lazyInject, WorkflowDocument } from '@flowgram.ai/free-layout-editor';

// import { lazyInject } from '@flowgram-adapter/free-layout-editor';
// import { type WorkflowDocument } from '@flowgram-adapter/free-layout-editor';

import { WorkflowSaveService } from '../services';

@injectable()
export class WorkflowPageContribution implements FlowDocumentContribution<WorkflowDocument>
{
  @lazyInject(WorkflowSaveService) 
  saveService: WorkflowSaveService;

  protected document: WorkflowDocument;

  /**
   * load data
   */
  async loadDocument(doc: WorkflowDocument): Promise<void> {
    this.document = doc;
    await this.saveService.loadDocument(doc);
  }
}

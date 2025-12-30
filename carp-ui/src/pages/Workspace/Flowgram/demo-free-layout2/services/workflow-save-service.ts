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

import { inject, injectable } from 'inversify';
import { delay, WorkflowDocument, WorkflowJSON } from '@flowgram.ai/free-layout-editor';

// This is not written dead, do not use it to judge the start node, please use flowNodeType
const START_NODE_ID = '100001';
const END_NODE_ID = '900001';
const RENDER_DELAY_TIME = 100;

/**
 * Create default, only start and end nodes
 */
function createDefaultJSON(): WorkflowJSON {
  return {
    nodes: [
      {
        id: START_NODE_ID,
        type: 'start',
        meta: {
          position: { x: 0, y: 0 },
        },
        data: {
          outputs: [
            {
              type: 'string',
              name: '',
              required: true,
            },
          ],
        },
      },
      {
        id: END_NODE_ID,
        type: 'end',
        meta: {
          position: { x: 1000, y: 0 },
        },
      },
    ],
    edges: [],
  };
}

@injectable()
export class WorkflowSaveService {

  protected workflowDocument: WorkflowDocument;

  /**
   * Get workflow schema json
   * @Param commitId process version information
   * @Param type type information for process version, commit or publish
   */
  loadWorkflowJson = async () => {
    let workflowJson: WorkflowJSON | undefined = await fetch('/data/flowgram/demo-free-layout.json')
      .then((response) => response.json());


    if (!workflowJson || workflowJson.nodes.length === 0) {
      workflowJson = createDefaultJSON()
    }

    if (!workflowJson.edges) {
      workflowJson.edges = [];
    }

    return workflowJson;
  };

  /**
   * Load document data
   */
  async loadDocument(doc: WorkflowDocument): Promise<void> {
    this.workflowDocument = doc;

    // load node information
    const [workflowJSON] = await Promise.all([
      this.loadWorkflowJson(),
    ]);

    console.log('Loading document workflow', workflowJSON)

    try {
      await this.workflowDocument.fromJSON(workflowJSON);
      await this.fitView();
    } catch (e) {
      throw e;
    }
  }
  /**
   * By default, the start node will be centered
   */
  async fitView(): Promise<void> {
    // Waiting for node rendering and layout calculation
    await delay(RENDER_DELAY_TIME);

    // Wait for DOM resize update
    await new Promise<void>(resolve => {
      window.requestAnimationFrame(() => resolve());
    });

    // execution layout
    this.workflowDocument.fitView(false);

    // Wait for the node to render after layout
    await delay(RENDER_DELAY_TIME);
  }

}

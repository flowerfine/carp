/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { nanoid } from 'nanoid';

import { WorkflowNodeType } from '../constants';
import { FlowNodeRegistry } from '../../typings';
import iconHTTP from '@/../public/nodes/flowgram/icon-http.svg';
import { formMeta } from './form-meta';

let index = 0;

export const RuleNodeRegistry: FlowNodeRegistry = {
  type: WorkflowNodeType.Rule,
  info: {
    icon: iconHTTP,
    description: 'And | Or filter rule node',
  },
  meta: {
    size: {
      width: 360,
      height: 390,
    },
  },
  onAdd() {
    return {
      id: `rule_${nanoid(5)}`,
      type: WorkflowNodeType.Rule,
      data: {
        title: `Rule_${++index}`,
        rulesValue: {
          ops: 'and',
          children: [
            { type: 'term', key: 'Key1', op: '>', value: 0 },
            {
              type: 'group',
              ops: 'or',
              children: [
                { type: 'term', key: 'Key2', op: '<', value: 20 },
                { type: 'term', key: 'Key3', op: '>', value: 10 },
              ],
            },
          ],
        },
        outputs: {
          type: 'object',
          properties: {
            rules: { type: 'object' }
          },
        },
      },
    };
  },
  formMeta: formMeta,
};

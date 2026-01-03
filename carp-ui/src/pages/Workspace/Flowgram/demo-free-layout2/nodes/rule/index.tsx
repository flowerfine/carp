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
            {
              key: {
                type: 'constant',
                content: 'Key1',
                schema: { type: 'string' }
              },
              op: '>',
              value: {
                type: 'constant',
                content: 0,
                schema: { type: 'integer' }
              }
            },
            {
              ops: 'or',
              children: [
                {
                  key: {
                    type: 'constant',
                    content: 'Key2',
                    schema: { type: 'string' }
                  },
                  op: '<',
                  value: {
                    type: 'constant',
                    content: 20,
                    schema: { type: 'integer' }
                  }
                },
                {
                  key: {
                    type: 'constant',
                    content: 'Key3',
                    schema: { type: 'string' }
                  },
                  op: '>',
                  value: {
                    type: 'constant',
                    content: 10,
                    schema: { type: 'integer' }
                  }
                },
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

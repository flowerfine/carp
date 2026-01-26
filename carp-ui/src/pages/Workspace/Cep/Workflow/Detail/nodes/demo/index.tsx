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

export const DemoNodeRegistry: FlowNodeRegistry = {
  type: WorkflowNodeType.Demo,
  info: {
    icon: iconHTTP,
    description: 'Demo node for testing purposes',
  },
  meta: {
    size: {
      width: 360,
      height: 390,
    },
  },
  onAdd() {
    return {
      id: `demo_${nanoid(5)}`,
      type: WorkflowNodeType.Demo,
      data: {
        title: `Demo_${++index}`,
        demoValues: {
          
        },
        outputs: {
          type: 'object',
          properties: {
            demos: { type: 'object' }
          },
        },
      },
    };
  },
  formMeta: formMeta,
};

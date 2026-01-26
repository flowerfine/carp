/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { IFlowConstantRefValue } from '@flowgram.ai/runtime-interface';
import { FlowNodeJSON } from '@flowgram.ai/free-layout-editor';
import { IFlowValue, IJsonSchema } from '@flowgram.ai/form-materials';

export interface RuleNodeJSON extends FlowNodeJSON {
  data: {
    title: string;
    outputs: IJsonSchema<'object'>;
    rules: IJsonSchema<'object'>;
    rulesValue: Record<string, IFlowConstantRefValue>;
  };
}

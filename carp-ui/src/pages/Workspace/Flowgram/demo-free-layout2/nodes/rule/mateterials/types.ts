/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { ConstantInputStrategy, IFlowValue } from '@flowgram.ai/form-materials';
import { IJsonSchema } from '@flowgram.ai/json-schema';


export interface PropsType {
  value?: Record<string, IFlowValue | undefined>;
  onChange: (value?: Record<string, IFlowValue | undefined>) => void;
  readonly?: boolean;
  hasError?: boolean;
  schema?: IJsonSchema;
  style?: React.CSSProperties;
  constantProps?: {
    strategies?: ConstantInputStrategy[];
    [key: string]: any;
  };
}

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

import { I18n, PlaygroundContext, type WorkflowNodeEntity } from '@flowgram.ai/free-layout-editor';
import { ValueExpression, ValueExpressionType } from '../../packages/workflow/base/types';

export interface ValueExpressionValidatorProps {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  value: any;
  /**
   * Is it required?
   */
  required?: boolean;
  /**
   * ValueExpression node
   */
  node: WorkflowNodeEntity;
  playgroundContext: PlaygroundContext;
  emptyErrorMessage?: string;
}

export const valueExpressionValidator = ({
  value,
  playgroundContext,
  node,
  required,
  emptyErrorMessage = I18n.t('workflow_detail_node_error_empty'),
}: ValueExpressionValidatorProps) => {
  const { variableValidationService } = playgroundContext;

  // check null value
  if (ValueExpression.isEmpty(value)) {
    if (!required) {
      return;
    }

    return emptyErrorMessage;
  }

  if (value.type === ValueExpressionType.REF) {
    return variableValidationService.isRefVariableEligible(value, node);
  }
};

/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field } from '@flowgram.ai/free-layout-editor';
import { IJsonSchema, JsonSchemaEditor, TypeScriptCodeEditor } from '@flowgram.ai/form-materials';
import { Divider } from '@douyinfe/semi-ui';

import { useIsSidebar, useNodeRenderContext } from '../../../hooks';
import { FormItem } from '../../../form-components';
import { CodeEditor } from '../mateterials/code-editor';
import { CodeValue, DEFAULT_CODE_VALUE } from '../mateterials/code-editor/types';

export function Code() {
  const isSidebar = useIsSidebar();
  const { readonly } = useNodeRenderContext();

  if (!isSidebar) {
    return null;
  }

  return (
    <>
      <Divider />
      <Field<CodeValue> name="script" defaultValue={DEFAULT_CODE_VALUE} >
          {({ field, fieldState }) => (
            <CodeEditor
              codeValue={field.value}
              onChange={(value) => field.onChange(value)}
              readonly={readonly} />
          )}
        </Field>
    </>
  );
}

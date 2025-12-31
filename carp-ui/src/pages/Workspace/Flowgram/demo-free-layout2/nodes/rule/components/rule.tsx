/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field } from '@flowgram.ai/free-layout-editor';
import { DisplayInputsValues, IFlowValue } from '@flowgram.ai/form-materials';

import { useIsSidebar, useNodeRenderContext } from '../../../hooks';
import { FormItem } from '../../../form-components';
import { RelationTree } from './relation-tree';

export function Rule() {
    const { readonly } = useNodeRenderContext();
    const isSidebar = useIsSidebar();

    if (!isSidebar) {
        return (
            <FormItem name="rules" required vertical type="object">
                <Field<Record<string, IFlowValue | undefined> | undefined> name="rulesValue">
                    {({ field }) => <DisplayInputsValues value={field.value} />}
                </Field>
            </FormItem>
        );
    }

    return (
        <FormItem name="rules" required vertical type="object">
            <Field<Record<string, IFlowValue | undefined> | undefined> name="rulesValue">
                {({ field }) => (
                    <div>
                        <RelationTree
                            value={{...field.value}}
                            onChange={(value) => field.onChange(value)}
                            readonly={readonly}
                        />
                    </div>
                )}
            </Field>
        </FormItem>
    );
}

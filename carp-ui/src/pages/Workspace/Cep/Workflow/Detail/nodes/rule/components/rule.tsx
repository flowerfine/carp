/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field, FormRenderProps, I18n } from '@flowgram.ai/free-layout-editor';
import { DisplayInputsValues, IFlowValue, InjectDynamicValueInput } from '@flowgram.ai/form-materials';
import { Col, Row, Select } from '@douyinfe/semi-ui';
import { IconPlus } from '@douyinfe/semi-icons';
import { Button } from 'antd';

import { useIsSidebar, useNodeRenderContext } from '../../../hooks';
import { FormItem } from '../../../form-components';
import { RuleNodeJSON } from '../types';
import FilterRules from '../mateterials/filter-rules';
import { IComponentProps, IFilterValueType } from '../mateterials/filter-rules/types';
import { INIT_DATA, INIT_ROW_VALUES, IRow } from '../mateterials/filter-rules/constants';

const MyInput = ({ rowKey, name, disabled, rowValues }: IComponentProps<IFilterValueType>) => (
    <div className="grid">
        <Row gutter={8}>
            <Col span={8}>
                <InjectDynamicValueInput
                    key={name + rowKey + 'left'}
                    style={{ flexGrow: 1 }}
                    readonly={disabled}
                    value={rowValues.left}
                    onChange={(v) => { }}
                />
            </Col>
            <Col span={4}>
                <Select
                    key={name + rowKey + 'operator'}
                    style={{ width: 65, maxWidth: 65, minWidth: 65 }}
                    size="small"
                    placeholder={"请选择"}
                    disabled={disabled}
                    value={rowValues.operator}
                    onChange={(v) => { }}
                    optionList={[
                        { label: '等于', value: '==' },
                        { label: '不等于', value: '!=' },
                        { label: '大于', value: '>' },
                        { label: '小于', value: '<' },
                    ]}
                />
            </Col>
            <Col span={12}>
                <InjectDynamicValueInput
                    key={name + rowKey + 'right'}
                    style={{ flexGrow: 1 }}
                    readonly={disabled}
                    value={rowValues.right}
                    onChange={(v) => { }}
                />
            </Col>
        </Row>
    </div>

);

export function Rule({ form }: FormRenderProps<RuleNodeJSON>) {
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
                        <FilterRules<IFilterValueType>
                            component={(props) => {
                                console.log('FilterRules component', props);
                                return <MyInput {...props} />
                            }}
                            notEmpty={{ data: false }}
                            disabled={readonly}
                            value={field.value}
                            onChange={(values) => {
                                console.log('FilterRules onChange', values);
                            }}
                        />
                        <Button
                            disabled={readonly}
                            icon={<IconPlus />}
                            size="small"
                        // onClick={() => form.setFieldsValue({ condition: INIT_DATA })}
                        >
                            {I18n.t('Add')}
                        </Button>
                    </div>
                )}
            </Field>
        </FormItem>
    );
}

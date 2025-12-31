/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { FormMeta, FormRenderProps } from '@flowgram.ai/free-layout-editor';
import { AssignRows, createInferAssignPlugin, createInferInputsPlugin, DisplayOutputs } from '@flowgram.ai/form-materials';
import { Divider } from '@douyinfe/semi-ui';

import { FormHeader, FormContent } from '../../form-components';
import { RuleNodeJSON } from './types';
import { defaultFormMeta } from '../default-form-meta';
import { Rule } from './components/rule';

export const FormRender = ({ form }: FormRenderProps<RuleNodeJSON>) => {

  return (
    <>
      <FormHeader />
      <FormContent>
        <Rule />
        <Divider />
        <DisplayOutputs displayFromScope />
      </FormContent>
    </>
  )
};

export const formMeta: FormMeta = {
  render: (props) => <FormRender {...props} />,
  effect: defaultFormMeta.effect,
  plugins: [
    createInferInputsPlugin({ sourceKey: 'rulesValue', targetKey: 'rules' })
  ],
};

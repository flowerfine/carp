/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { FormMeta, FormRenderProps, I18n } from '@flowgram.ai/free-layout-editor';
import { AssignRows, createInferAssignPlugin, createInferInputsPlugin, DisplayOutputs } from '@flowgram.ai/form-materials';
import { Divider, Switch } from '@douyinfe/semi-ui';

import { FormHeader, FormContent } from '../../form-components';
import { RuleNodeJSON } from './types';
import { defaultFormMeta } from '../default-form-meta';
import { withNodeConfigForm } from '../../../node-registries/common/hocs';
import { Section, useWatch } from '../../../form';

export const FormRender = withNodeConfigForm(() => {

  const tabName = useWatch<string>('trigger.tab');

  return (
    <>
      <FormHeader />
      <FormContent>


        <Section
          title={I18n.t('basic_setting')}
          tooltip="12345"
          actions={[<Switch

            size="small"
          />
          ]}
        >

        </Section>
        <Divider />
        <DisplayOutputs displayFromScope />
      </FormContent>
    </>
  )
});

export const formMeta: FormMeta<FormData> = {
  render: () => <FormRender />,
  effect: defaultFormMeta.effect,
  plugins: [
    createInferInputsPlugin({ sourceKey: 'demoValues', targetKey: 'demos' })
  ],
};

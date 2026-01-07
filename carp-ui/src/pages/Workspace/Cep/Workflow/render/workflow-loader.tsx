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

import React, { useLayoutEffect, useMemo } from 'react';
import { FlowRendererRegistry, useService, WorkflowDocument } from '@flowgram.ai/free-layout-editor';

import styles from './index.module.less';
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import { useGlobalState } from '../Detail/hooks';

interface WorkflowLoaderProps {
  cepWorkflow: WorkspaceCepAPI.CepWorkflow;
}

export const WorkflowLoader: React.FC<WorkflowLoaderProps> = (props) => {
  const doc = useService<WorkflowDocument>(WorkflowDocument);
  const renderRegistry = useService<FlowRendererRegistry>(FlowRendererRegistry);
  const workflowState = useGlobalState();

  useMemo(() => renderRegistry.init(), [renderRegistry]);
  // Synchronize component properties to globalStatus
  useMemo(() => {
    const { cepWorkflow } = props;
    workflowState.updateConfig({
      workflowId: cepWorkflow.id,
      info: cepWorkflow,
    });
  }, [props]);

  useLayoutEffect(() => {
    // load data
    doc.load();
    // Destroy data
    return () => doc.dispose();
  }, [doc]);

  return <div className={styles.playgroundLoad} />;
};

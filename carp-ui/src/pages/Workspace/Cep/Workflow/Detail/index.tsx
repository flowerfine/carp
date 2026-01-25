/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { forwardRef, useEffect, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { history, useIntl, useLocation } from '@umijs/max';
import { EditorRenderer, FreeLayoutEditorProvider } from '@flowgram.ai/free-layout-editor';
import { DockedPanelLayer } from '@flowgram.ai/panel-manager-plugin';
import '@flowgram.ai/free-layout-editor/index.css';

import './styles/index.css';
import { nodeRegistries } from './nodes';
import { useEditorProps } from './hooks';
import { WorkflowPageContainerModule } from '../container/workflow-page-container-module';
import { WorkflowLoader } from '../render/workflow-loader';
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';


const Editor = () => {
  const intl = useIntl()
  const urlParams = useLocation();
  const cepWorkflow = urlParams.state as WorkspaceCepAPI.CepWorkflow;
  const editorProps = useEditorProps(nodeRegistries, cepWorkflow);

  return (
    <PageContainer breadcrumbRender={false} title={intl.formatMessage({ id: 'menu.workspace.cep.workflow.detail' })}
      content={false}
      onBack={() => history.back()}>
      <div className="doc-free-feature-overview" style={{ height: '80vh' }}>
        <FreeLayoutEditorProvider {...editorProps}
          containerModules={[
            WorkflowPageContainerModule
          ]}
        >
          <div className="demo-container">
            <WorkflowLoader cepWorkflow={cepWorkflow} />
            <DockedPanelLayer>
              <EditorRenderer className="demo-editor" />
            </DockedPanelLayer>
          </div>
        </FreeLayoutEditorProvider>
      </div>
    </PageContainer>

  );
};

export default Editor;

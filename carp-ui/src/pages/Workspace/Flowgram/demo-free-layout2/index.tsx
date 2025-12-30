/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { forwardRef, useEffect, useRef } from 'react';
import { createRoot } from 'react-dom/client';
import { unstableSetCreateRoot } from '@flowgram.ai/form-materials';

import '@flowgram.ai/free-layout-editor/index.css';
import './styles/index.css';
import { nodeRegistries } from './nodes';
import { useEditorProps } from './hooks';
import { WorkflowPageContainerModule } from './container/workflow-page-container-module';
import { EditorRenderer, FreeLayoutEditorProvider } from '@flowgram.ai/free-layout-editor';
import { DockedPanelLayer } from '@flowgram.ai/panel-manager-plugin';
import { WorkflowLoader } from '../render/workflow-loader';

unstableSetCreateRoot(createRoot);

const Editor = forwardRef((props) => {
  const editorProps = useEditorProps(nodeRegistries);

  return (
    <div className="doc-free-feature-overview" style={{ height: '80vh' }}>
      <FreeLayoutEditorProvider {...editorProps}
        containerModules={[
          WorkflowPageContainerModule
        ]}
      >
        <div className="demo-container">
          <WorkflowLoader />
          <DockedPanelLayer>
            <EditorRenderer className="demo-editor" />
          </DockedPanelLayer>
        </div>
      </FreeLayoutEditorProvider>
    </div>
  );
});

const EditorWrapper: React.FC = () => {
  const container = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (container.current) {
      const root = createRoot(container.current);
      root.render(<Editor />);
    }
  }, [container]);

  return <div ref={container}></div>
};

export default EditorWrapper;

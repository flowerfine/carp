/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useEffect, useRef } from 'react';
import { createRoot } from 'react-dom/client';
import { DockedPanelLayer } from '@flowgram.ai/panel-manager-plugin';
import { EditorRenderer, FreeLayoutEditorProvider } from '@flowgram.ai/free-layout-editor';
import { unstableSetCreateRoot } from '@flowgram.ai/form-materials';

import '@flowgram.ai/free-layout-editor/index.css';
import './styles/index.css';
import { nodeRegistries } from './nodes';
import { initialData } from './initial-data';
import { useEditorProps } from './hooks';


unstableSetCreateRoot(createRoot);

const Editor = () => {
  const editorProps = useEditorProps(initialData, nodeRegistries);

  console.log(JSON.stringify(initialData, null, 2))

  return (
    <div className="doc-free-feature-overview" style={{ height: '80vh' }}>
      <FreeLayoutEditorProvider {...editorProps}>
        <div className="demo-container">
          <DockedPanelLayer>
            <EditorRenderer className="demo-editor" />
          </DockedPanelLayer>
        </div>
      </FreeLayoutEditorProvider>
    </div>
  );
};

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

// const root = createRoot(document.getElementById('root')!);
// root.render(<App />);

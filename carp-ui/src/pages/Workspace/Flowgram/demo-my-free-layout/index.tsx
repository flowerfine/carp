import { useEffect, useRef } from "react";
import { createRoot } from "react-dom/client";
import { PageContainer } from "@ant-design/pro-components"
import { useIntl, history } from '@umijs/max';
import '@flowgram.ai/free-layout-editor/index.css';
import { EditorRenderer, FreeLayoutEditorProvider, FreeLayoutPluginContext } from "@flowgram.ai/free-layout-editor";
import { DockedPanelLayer } from "@flowgram.ai/panel-manager-plugin";
import { unstableSetCreateRoot } from "@flowgram.ai/form-materials";
import { useEditorProps } from './use-editor-props';
import { Tools } from './tools';
import { Minimap } from './minimap';
import { AddNode } from './add-node';
import '../demo-free-layout/styles/index.css';

// 这玩意没啥用，没感觉出来有用
unstableSetCreateRoot(createRoot);

const CepRuleDetailWeb: React.FC = () => {
    const editorProps = useEditorProps();

    return (
        <div className="doc-free-feature-overview" style={{ height: '80vh' }}>
            <FreeLayoutEditorProvider {...editorProps}>
                <div className="demo-container">
                    <DockedPanelLayer>
                        <EditorRenderer className="demo-editor" />
                        <Tools />
                        <Minimap />
                        <AddNode />
                    </DockedPanelLayer>
                </div>
            </FreeLayoutEditorProvider>
        </div>
    )
}

const CepRuleDetailWebWrapper: React.FC = () => {
    const container = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (container.current) {
            const root = createRoot(container.current);
            root.render(<CepRuleDetailWeb />);
        }
    }, [container]);

    return <div ref={container}></div>
};

export default CepRuleDetailWebWrapper;
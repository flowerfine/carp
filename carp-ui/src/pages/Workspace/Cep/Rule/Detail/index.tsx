import { useRef } from "react";
import { PageContainer } from "@ant-design/pro-components"
import { useIntl, history } from '@umijs/max';
import '@flowgram.ai/free-layout-editor/index.css';
import { EditorRenderer, FreeLayoutEditorProvider, FreeLayoutPluginContext } from "@flowgram.ai/free-layout-editor";
import { useEditorProps } from './use-editor-props';
import { Tools } from './tools';
import { Minimap } from './minimap';
import { AddNode } from './add-node';

const CepRuleDetailWeb: React.FC = () => {
    const intl = useIntl();
    const ref = useRef<FreeLayoutPluginContext>();
    const editorProps = useEditorProps();

    return (
        <PageContainer title={intl.formatMessage({ id: 'menu.workspace.cep.rule.detail' })}
            content={intl.formatMessage({ id: 'menu.workspace.cep.rule.detail.desc' })}
            onBack={() => history.back()}>
            <div style={{ height: '60vh' }}>
                <FreeLayoutEditorProvider  {...editorProps} ref={ref}>
                    <EditorRenderer />
                    <Tools />
                    <Minimap />
                    <AddNode />
                </FreeLayoutEditorProvider>
            </div>

        </PageContainer>
    )
}

export default CepRuleDetailWeb;
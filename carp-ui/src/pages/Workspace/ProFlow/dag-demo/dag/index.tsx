import React, {useEffect} from 'react';
import {PageContainer} from '@ant-design/pro-components';
import {useIntl, useLocation} from '@umijs/max';
import { FlowEditor, FlowEditorProvider, useFlowEditor } from '@ant-design/pro-flow';
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import useStyles from './style';
import {StringRender} from "@/pages/Workspace/ProFlow/dag-demo/dag/StringNode";

const ProFlowDemo = () => {
  const editor = useFlowEditor();
  const { styles } = useStyles();

  useEffect(() => {
    editor.addNode({
      id: 'a1',
      type: 'StringNode',
      position: { x: 0, y: 100 },
      data: {
        title: 'String Node',
        handles: {},
      },
    });
  }, [editor]);

  return (
    <div className={styles.container}>
      <FlowEditor nodeTypes={{ StringNode: StringRender }} />
    </div>
  );
};

const WorkflowDagDetailWeb: React.FC = () => {
  const intl = useIntl();
  const data = useLocation().state as WorkspaceWorkflowAPI.WorkflowDefinition;

  return (
    <PageContainer title={intl.formatMessage({id: 'menu.workspace.pro-flow.dag-detail'})}
                   content={intl.formatMessage({id: 'menu.workspace.pro-flow.dag-detail.desc'})}>

      <FlowEditorProvider>
        <ProFlowDemo />
      </FlowEditorProvider>

    </PageContainer>
  );
};

export default WorkflowDagDetailWeb;

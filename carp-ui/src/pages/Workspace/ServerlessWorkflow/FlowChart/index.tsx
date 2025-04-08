import React, {useEffect, useRef} from "react";
import {PageContainer} from "@ant-design/pro-components";
import {useIntl} from "@umijs/max";
import {convertToMermaidCode} from '@serverlessworkflow/sdk';
import mermaid, {MermaidConfig} from "mermaid";

const mermaidConfig: MermaidConfig = {
  startOnLoad: true,
  theme: 'default',
  securityLevel: 'loose',
}

const WorkspaceServerlessWorkflowFlowChartWeb: React.FC = () => {
  const intl = useIntl();
  const ref = useRef(null);

  useEffect(() => {
    if (ref.current) {
      mermaid.initialize(mermaidConfig);
      mermaid.contentLoaded();

      try {
        const mermaidCode = convertToMermaidCode(workflow)
        mermaid.parse(mermaidCode);
        ref.current.innerHTML = mermaidCode;
        mermaid.run({
          nodes: [ref.current],
        });
      } catch (error) {
        console.error('Mermaid error:', error);
        ref.current.innerHTML = error.message;
      }
    }
  }, []);

  const workflow = {
    document: {
      dsl: '1.0.0',
      name: 'using-plain-object',
      version: '1.0.0',
      namespace: 'default',
    },
    do: [
      {
        step1: {
          set: {
            variable: 'my first workflow',
          },
        },
      },
    ],
  };

  return (
    <PageContainer content={intl.formatMessage({id: 'menu.workspace.serverless-workflow.config.desc'})}>
      <div ref={ref}></div>
    </PageContainer>
  )
}

export default WorkspaceServerlessWorkflowFlowChartWeb

import React, {useEffect, useRef} from "react";
import {useIntl} from "@umijs/max";
import mermaid, {MermaidConfig} from "mermaid";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {ServerlessWorkflowInstanceService} from "@/services/workspace/workflow/serverless-workflow-instance.service";
import {Props} from "@/typings";

const mermaidConfig: MermaidConfig = {
  startOnLoad: true,
  theme: 'default',
  securityLevel: 'loose',
}

const WorkspaceServerlessWorkflowInstanceDetailMermaidWeb: React.FC<Props<WorkspaceWorkflowAPI.ServerlessWorkflowInstance>> = ({data}) => {
  const intl = useIntl();
  const ref = useRef(null);

  useEffect(() => {
    if (ref.current) {
      mermaid.initialize(mermaidConfig);
      mermaid.contentLoaded();

      try {
        ServerlessWorkflowInstanceService.toMermaid(data.id).then(response => {
          if (response.success && response.data) {
            mermaid.parse(response.data);
            ref.current.innerHTML = response.data;
            mermaid.run({
              nodes: [ref.current],
            });
          }
        })
      } catch (error) {
        ref.current.innerHTML = error.message;
      }
    }
  }, [ref]);

  return (
    <div style={{
      textAlign: "center"
    }}>
      <div ref={ref}></div>
    </div>
  )
}

export default WorkspaceServerlessWorkflowInstanceDetailMermaidWeb;

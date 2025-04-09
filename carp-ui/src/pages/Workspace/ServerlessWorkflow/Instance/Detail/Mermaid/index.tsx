import React, {useEffect, useRef} from "react";
import {useIntl, useLocation} from "@umijs/max";
import mermaid, {MermaidConfig} from "mermaid";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowInstanceService} from "@/services/workspace/workflow/workflow-instance.service";

const mermaidConfig: MermaidConfig = {
  startOnLoad: true,
  theme: 'default',
  securityLevel: 'loose',
}

const WorkspaceServerlessWorkflowInstanceDetailMermaidWeb: React.FC = () => {
  const intl = useIntl();
  const ref = useRef(null);
  const workflowInstance = useLocation().state as WorkspaceWorkflowAPI.WorkflowInstance;

  useEffect(() => {
    if (ref.current) {
      mermaid.initialize(mermaidConfig);
      mermaid.contentLoaded();

      try {
        WorkflowInstanceService.toMermaid(workflowInstance.id).then(response => {
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

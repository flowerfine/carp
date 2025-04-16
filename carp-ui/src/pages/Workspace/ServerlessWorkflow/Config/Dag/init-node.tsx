import React, {useEffect} from "react";
import {useGraphInstance} from "@antv/xflow";
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {
  ServerlessWorkflowDefinitionService
} from "@/services/workspace/workflow/serverless-workflow-definition.service";

const InitNode: React.FC<ModalFormProps<WorkspaceWorkflowAPI.ServerlessWorkflowDefinition>> = ({data}) => {
  const graph = useGraphInstance();

  useEffect(() => {
    if (graph) {
      ServerlessWorkflowDefinitionService.toX6Graph(data?.id).then(response => {
        if (response.success && response.data) {
          if (response.data.nodes) {
            graph.addNodes(response.data.nodes);
            if (response.data.edges) {
              graph.addEdges(response.data.edges);
            }
          }
          graph.zoomToFit({maxScale: 1});
        }
      })
    }
  }, [graph]);

  return null;
};

export {InitNode};

import React, {useEffect} from "react";
import {useGraphInstance} from "@antv/xflow";
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowInstanceService} from "@/services/workspace/workflow/workflow-instance.service";

const InitNode: React.FC<ModalFormProps<WorkspaceWorkflowAPI.WorkflowInstance>> = ({data}) => {
  const graph = useGraphInstance();

  useEffect(() => {
    if (graph) {
      WorkflowInstanceService.getGraph(data?.id).then(response => {
        if (response.success && response.data) {
          if (response.data.nodes) {
            graph.addNodes(response.data.nodes);
          }
          if (response.data.edges) {
            graph.addEdges(response.data.edges);
          }
          graph.zoomToFit({maxScale: 1});
        }
      })
    }
  }, [graph]);

  return null;
};

export {InitNode};

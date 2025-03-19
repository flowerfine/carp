import React, {useEffect} from "react";
import {useGraphInstance} from "@antv/xflow";
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowService} from "@/services/workspace/workflow/workflow.service";

const InitNode: React.FC<ModalFormProps<WorkspaceWorkflowAPI.WorkflowDefinition>> = ({data}) => {
  const graph = useGraphInstance();

  useEffect(() => {
    if (graph) {
      WorkflowService.getGraph(data?.id).then(response => {
        if (response.success && response.data) {
          console.log('InitNode response', response)
        }
      })
      fetch('/data/serverless-workflow.json')
        .then((response) => response.json())
        .then((data) => {
          // 使用 graph.fromJSON 方法导入进去的节点，无法被键盘快捷键处理
          // graph.fromJSON(data)
          if (data.nodes) {
            graph.addNodes(data.nodes);
          }
          if (data.edges) {
            graph.addEdges(data.edges);
          }
          graph.zoomToFit({maxScale: 1});
        })
    }
  }, [graph]);

  return null;
};

export {InitNode};

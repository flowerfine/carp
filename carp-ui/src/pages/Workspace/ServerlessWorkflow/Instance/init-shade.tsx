import React, {useEffect} from "react";
import {useGraphInstance, useGraphStore} from "@antv/xflow";

const InitNode: React.FC = () => {
  const graph = useGraphInstance();
  const addNodes = useGraphStore((state) => state.addNodes);
  const addEdges = useGraphStore((state) => state.addEdges);

  useEffect(() => {
    if (graph) {
      fetch('/data/serverless-workflow.json')
        .then((response) => response.json())
        .then((data) => {
          // 使用 graph.fromJSON 方法导入进去的节点，无法被键盘快捷键处理
          // graph.fromJSON(data)
          graph.addNodes(data.nodes);
          graph.addEdges(data.edges);
          graph.zoomToFit({ maxScale: 1 });
        })
    }
  }, [graph]);

  return null;
};

export {InitNode};

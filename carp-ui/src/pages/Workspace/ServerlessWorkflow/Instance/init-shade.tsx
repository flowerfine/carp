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
          graph.fromJSON(data)
          graph.zoomToFit({ maxScale: 1 });
        })
    }
  }, [graph]);

  return null;
};

export {InitNode};

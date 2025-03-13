import React, {useEffect} from "react";
import {useIntl} from "@umijs/max";
import {useGraphInstance, useGraphStore} from "@antv/xflow";

const InitNode: React.FC = () => {
  const intl = useIntl()
  const graph = useGraphInstance();

  useEffect(() => {
    if (graph) {
      fetch('/data/cicd.json')
        .then((response) => response.json())
        .then((data) => {
          graph.fromJSON(data)
          const zoomOptions = {
            padding: {
              left: 10,
              right: 10,
            },
          }
          graph.zoomToFit(zoomOptions)
        })
    }
  }, [graph]);

  return null;
};

export {InitNode};

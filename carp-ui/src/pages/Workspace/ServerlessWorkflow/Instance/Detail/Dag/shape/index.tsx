import {Graph, Node, register, XFlow} from "@antv/xflow";
import BasicNode from "@/components/X6/Shape/BasicNode";
import React from "react";

const SERVERLESS_WORKFLOW_INSTNACE_NODE = 'serverless-workflow-instance-node';
const SERVERLESS_WORKFLOW_INSTNACE_EDGE = 'serverless-workflow-instance-edge';

const ServerlessWorkflowInstanceNode = ({node}: { node: Node }) => {

  return (
    <XFlow>
      <BasicNode
        node={node}
      />
    </XFlow>
  );
}

register({
  shape: SERVERLESS_WORKFLOW_INSTNACE_NODE,
  width: 240,
  height: 60,
  component: ServerlessWorkflowInstanceNode,
  // port默认不可见. stroke 和 fill 设置为 transparent
  ports: {
    groups: {
      out: {
        position: 'right',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#85A5FF',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },

      in: {
        position: 'left',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#85A5FF',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },
    },
  },
})

Graph.registerEdge(
  SERVERLESS_WORKFLOW_INSTNACE_EDGE,
  {
    markup: [
      {
        tagName: 'path',
        selector: 'wrap',
        attrs: {
          fill: 'none',
          cursor: 'pointer',
          stroke: 'transparent',
          strokeLinecap: 'round',
        },
      },
      {
        tagName: 'path',
        selector: 'line',
        attrs: {
          fill: 'none',
          pointerEvents: 'none',
        },
      },
    ],
    connector: {name: 'smooth'},
    attrs: {
      wrap: {
        connection: true,
        strokeWidth: 10,
        strokeLinejoin: 'round',
      },
      line: {
        connection: true,
        stroke: '#A2B1C3',
        strokeWidth: 1,
        targetMarker: {
          name: 'classic',
          size: 6,
        },
      },
    }
  },
  true)

export {SERVERLESS_WORKFLOW_INSTNACE_NODE, SERVERLESS_WORKFLOW_INSTNACE_EDGE}


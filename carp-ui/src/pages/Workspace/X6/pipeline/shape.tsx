import React, {useState} from "react";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import BasicNode from "@/components/X6/Shape/BasicNode";

const PIPELINE_NODE = 'pipeline-node';
const PIPELINE_EDGE = 'pipeline-edge';

const X6PipelineNode = ({node}: { node: Node }) => {
  const [openDrawerForm, setOpenDrawerForm] = useState<boolean>(false)

  return (
    <XFlow>
      <div onDoubleClick={() => setOpenDrawerForm(true)}>
        <BasicNode
          node={node}
        />
      </div>
    </XFlow>
  );
}

register({
  shape: PIPELINE_NODE,
  width: 240,
  height: 60,
  component: X6PipelineNode,
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
  PIPELINE_EDGE,
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

export {
  PIPELINE_NODE,
  PIPELINE_EDGE
};

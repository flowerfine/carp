import React from "react";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import useStyles from './style';
import PipelineEdges from "@/pages/Workspace/X6/pipeline/shape/edges";

const PIPELINE_NODE = 'pipeline-node';
const PIPELINE_EDGE = 'pipeline-edge';

const X6PipelineNode = ({node}: { node: Node }) => {
  const {styles} = useStyles();
  const {label, meta, attrs} = node.getData();
  // const {stages = []} = attrs;
  const stages = [];
  const isEditMode = true;
  const heights = [];

  const sumHeights = () => {
    return heights.slice(0, stages.length).map(height => {
      let tempHeight = 0
      return height.map(_height => {
        tempHeight += _height
        return tempHeight
      })
    })
  }

  return (
    <XFlow>
      <PipelineEdges
        heights={sumHeights()}
        index={-1}
        isEditMode={isEditMode}
        onInsertColumn={(index) => {
        }}/>
    </XFlow>
  );
}

register({
  shape: PIPELINE_NODE,
  width: 240,
  height: 60,
  component: X6PipelineNode,
  draggable: false, // 不可拖动
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
  }
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

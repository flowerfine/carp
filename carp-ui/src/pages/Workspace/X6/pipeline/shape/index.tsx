import React from "react";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import useStyles from './style';
import {ProCard} from "@ant-design/pro-components";

const PIPELINE_NODE = 'pipeline-node';
const PIPELINE_EDGE = 'pipeline-edge';

const X6PipelineNode = ({node}: { node: Node }) => {
  const {styles} = useStyles();
  const {label, meta, attrs} = node.getData();
  const {children} = attrs



  return (
    <XFlow>
      <ProCard.Group title={label}
                     gutter={8}
                     wrap
                     bordered hoverable headerBordered
      >
        {children?.map(child => {
          return (
            <ProCard
              title={child.title}
              layout={"center"} bordered
            >
              {child.des}
            </ProCard>
          )
        })}
      </ProCard.Group>
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
        position: {
          name: 'right',
          args: {
            dx: 2,
          }
        },
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: 'transparent',
            strokeWidth: 1,
            fill: 'transparent',
          },
        },
      },

      in: {
        position: {
          name: 'left',
          args: {
            dx: -2,
          }
        },
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: 'transparent',
            strokeWidth: 1,
            fill: 'transparent',
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
        strokeWidth: 5,
        strokeLinejoin: 'round',
      },
      line: {
        connection: true,
        stroke: '#A2B1C3',
        strokeWidth: 2,
      },
    }
  },
  true)

export {
  PIPELINE_NODE,
  PIPELINE_EDGE
};

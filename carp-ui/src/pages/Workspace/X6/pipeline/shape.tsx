import React from "react";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import useStyles from './shapeStyle';
import {PlusOutlined} from "@ant-design/icons";
import {Button} from "antd";
import classnames from "classnames";

const STROKEWIDTH = 2
const EDGES_LENGTH = 35
const POINT_R = 6
const CURVE_R = 10

const PIPELINE_NODE = 'pipeline-node';
const PIPELINE_EDGE = 'pipeline-edge';

const X6PipelineNode = ({node}: { node: Node }) => {
  const {styles} = useStyles();

  const renderEditButton = () => {
    return (
      <g className={styles.editButton}>
        <circle
          cx={EDGES_LENGTH}
          cy={POINT_R}
          r="10"
          stroke="#d8dee5"
          strokeDasharray="3,3"
          fill="white"
          strokeWidth={STROKEWIDTH}
        />
        <line
          x1={EDGES_LENGTH - 5}
          x2={EDGES_LENGTH + 5}
          y1={POINT_R}
          y2={POINT_R}
          stroke="black"
        />
        <line
          x1={EDGES_LENGTH}
          x2={EDGES_LENGTH}
          y1={POINT_R - 5}
          y2={POINT_R + 5}
          stroke="black"
        />
      </g>
    )
  }


  const renderBoldLines = () => {
    // return (
    //   <>
    //     {this.renderLines(heights[index], 'left')}
    //     {this.renderLines(heights[index + 1], 'right')}
    //   </>
    // )
  }


  return (
    <XFlow>
      <div className={classnames(styles.connectorLine)}>
        <div className={classnames(styles.connectorDot)}></div>
        <div className={classnames(styles.connectorLineSegment)}></div>
        <Button
          className={classnames(styles.connectorButton)}
          type="primary" shape="circle" icon={<PlusOutlined />} size="small"
        />
        <div className={classnames(styles.connectorLineSegment)}></div>
        <div className={classnames(styles.connectorDot)}></div>
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

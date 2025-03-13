import React from "react";
import {getIntl, getLocale} from "@umijs/max";
import {Graph, Node, Path, register, XFlow} from "@antv/xflow";
import useStyles from './style';

const FLOWISE_NODE = 'flowise-node';
const FLOWISE_EDGE = 'flowise-curve';
const FLOWISE_CONNECTOR = 'flowise-onnector';

const FlowiseNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  const { styles } = useStyles();
  const data = node?.getData()

  return (
    <XFlow>
      <div className={styles.pipeNodeWrap}>
        测试信息
      </div>
    </XFlow>
  );
}

register({
  shape: FLOWISE_NODE,
  width: 212,
  height: 48,
  component: FlowiseNode,
  // port默认不可见
  ports: {
    groups: {
      in: {
        position: 'left',
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

      out: {
        position: {
          name: 'right',
          args: {
            dx: -32,
          },
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
  },
})

// 注册连线
Graph.registerConnector(
  FLOWISE_CONNECTOR,
  (s, t) => {
    const hgap = Math.abs(t.x - s.x)
    const path = new Path()
    path.appendSegment(
      Path.createSegment('M', s.x - 4, s.y),
    )
    path.appendSegment(
      Path.createSegment('L', s.x + 12, s.y),
    )
    // 水平三阶贝塞尔曲线
    path.appendSegment(
      Path.createSegment(
        'C',
        s.x < t.x
          ? s.x + hgap / 2
          : s.x - hgap / 2,
        s.y,
        s.x < t.x
          ? t.x - hgap / 2
          : t.x + hgap / 2,
        t.y,
        t.x - 6,
        t.y,
      ),
    )
    path.appendSegment(
      Path.createSegment('L', t.x + 2, t.y),
    )

    return path.serialize()
  },
  true,
)

Graph.registerEdge(
  FLOWISE_EDGE,
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
    connector: {name: FLOWISE_CONNECTOR},
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
    },
  },
  true)

export {FLOWISE_NODE, FLOWISE_EDGE, FLOWISE_CONNECTOR, FlowiseNode};

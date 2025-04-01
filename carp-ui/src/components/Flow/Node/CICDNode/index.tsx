import React from "react";
import {getIntl, getLocale} from "@umijs/max";
import {Graph, Node, Path, register, XFlow} from "@antv/xflow";
import useStyles from './style';

const CICD_NODE = 'ci-cd-node';
const CICD_EDGE = 'ci-cd-curve';
const CICD_CONNECTOR = 'ci-cd-onnector';

interface PipeNodeChild {
  title: string;
  des: string;
  logo: string;
}

interface PipeNode {
  stepTitle: string;
  title: string;
  des: string;
  logo: string;
  needSwitch?: boolean;
  children?: PipeNodeChild[];
}

const CICDNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  const { styles } = useStyles();
  const data = node?.getData()
  const { stepTitle, title, des, logo, needSwitch = false, children = [] } = data;

  return (
    <XFlow>
      <div className={styles.pipeNodeWrap}>
        <div className={styles.stepTitle}>{stepTitle}</div>

        <div className={styles.pipeNode}>
          <div className={styles.mainBox}>
            <div className={styles.logo}>
              <img src={logo} alt="" />
            </div>
            <div className={styles.wrap}>
              <div className={styles.title}>{title}</div>
              <div className={styles.des}>{des}</div>
            </div>
            {needSwitch && (
              <div className={styles.pipeNodeRight}>
                <div className={styles.switch}>
                  <div className={styles.switchIcon}></div>
                </div>
              </div>
            )}
          </div>
          {children.length > 0 && (
            <div className={styles.children}>
              {children.map((item, index) => (
                <div style={{ position: 'relative' }} key={item.title + index}>
                  <div className={styles.childrenBox}>
                    <div className={styles.logo}>
                      <img src={item.logo} alt="" />
                    </div>
                    <div className={styles.wrap}>
                      <div className={styles.title}>{item.title}</div>
                      <div className={styles.des}>{item.des}</div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </XFlow>
  );
}

register({
  shape: CICD_NODE,
  width: 212,
  height: 48,
  component: CICDNode,
  // 不可拖拽
  draggable: false,
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
  CICD_CONNECTOR,
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
  CICD_EDGE,
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
    connector: {name: CICD_CONNECTOR},
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

export {CICD_NODE, CICD_EDGE, CICD_CONNECTOR, CICDNode};

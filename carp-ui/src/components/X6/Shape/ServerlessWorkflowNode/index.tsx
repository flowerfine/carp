import React, {useState} from "react";
import {Flex, Image, Typography} from "antd";
import {ProCard} from "@ant-design/pro-components";
import {getIntl, getLocale} from "@umijs/max";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import useStyles from './style';

const SERVERLESS_WORKFLOW_NODE = 'serverless-workflow-node';
const SERVERLESS_WORKFLOW_EDGE = 'serverless-workflow-edge';

const ServerlessWorkflowNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  const {styles} = useStyles();
  const data = node?.getData()
  const {name, type, status, statusMsg} = data
  const [state, setState] = useState<{ plusActionSelected: boolean, }>({plusActionSelected: false})

  // 鼠标进入矩形主区域的时候显示连接桩
  const onMainMouseEnter = () => {
    // 获取该节点下的所有连接桩
    const ports = node.getPorts() || []
    ports.forEach((port) => {
      node.setPortProp(port.id, 'attrs/circle', {
        fill: '#fff',
        stroke: '#85A5FF',
      })
    })
  }

  // 鼠标离开矩形主区域的时候隐藏连接桩
  const onMainMouseLeave = () => {
    // 获取该节点下的所有连接桩
    const ports = node.getPorts() || []
    ports.forEach((port) => {
      node.setPortProp(port.id, 'attrs/circle', {
        fill: 'transparent',
        stroke: 'transparent',
      })
    })
  }

  return (
    <XFlow>
      <ProCard
        onMouseEnter={onMainMouseEnter}
        onMouseLeave={onMainMouseLeave}
        title={
          <Flex align={'center'} gap={'small'}>
            <Image src={data.dndMeta?.icon} alt={data.dndMeta?.type} preview={false}/>
            <Typography.Text style={{
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              maxWidth: '200px'
            }}
            >{data.label}</Typography.Text>
          </Flex>
        }
        tooltip={data.dndMeta?.description}
        extra={"extra"}
      >
        <div>{data.label}</div>

      </ProCard>
    </XFlow>
  );
}

register({
  shape: SERVERLESS_WORKFLOW_NODE,
  width: 212,
  height: 28,
  component: ServerlessWorkflowNode,
  // port默认不可见. stroke 和 fill 设置为 transparent
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

Graph.registerEdge(
  SERVERLESS_WORKFLOW_EDGE,
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
    },
    tools: {
      name: "button-remove",
      args: {distance: "50%"}
    },
  },
  true)

export {
  SERVERLESS_WORKFLOW_NODE,
  SERVERLESS_WORKFLOW_EDGE,
  ServerlessWorkflowNode
};

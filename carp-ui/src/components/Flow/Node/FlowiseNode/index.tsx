import React from "react";
import {Divider, Flex, Image, theme, Typography} from "antd";
import {ProCard} from "@ant-design/pro-components";
import {getIntl, getLocale} from "@umijs/max";
import {Graph, Node, Path, register, XFlow} from "@antv/xflow";
import NodeInputHandler from "@/components/Flow/Node/FlowiseNode/NodeInputHandler";
import NodeOutputHandler from "@/components/Flow/Node/FlowiseNode/NodeOutputHandler";
import NodeInputAnchorHandler from "@/components/Flow/Node/FlowiseNode/NodeInputAnchorHandler";

const {useToken} = theme;

const FLOWISE_NODE = 'flowise-node';
const FLOWISE_EDGE = 'flowise-curve';
const FLOWISE_CONNECTOR = 'flowise-onnector';

const FlowiseNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  const {token} = useToken();
  const data = node?.getData() as FlowiseNodeAPI.INode


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
        title={
          <Flex align={'center'} gap={'small'}>
            <Image src={data.icon} alt={data.type} preview={false}/>
            <Typography.Text style={{
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              maxWidth: '200px'
            }}
            >{data.label}</Typography.Text>
          </Flex>
        }
        tooltip={data.description}
      >
        <Flex vertical>
          {(data.inputAnchors.length > 0 || data.inputParams.length > 0) && (
            <>
              <Divider/>
              <Flex vertical style={{background: 'gray', p: 1}}>
                <Typography.Title level={5} style={{fontWeight: 500, textAlign: 'center'}}>
                  Inputs
                </Typography.Title>
              </Flex>
              <Divider/>
            </>
          )}
          {data.inputAnchors.map((inputAnchor, index) => (
            <NodeInputAnchorHandler key={index} node={node} inputAnchor={inputAnchor} data={data}/>
          ))}
          {data.inputParams.filter((inputParam) => !inputParam.hidden).map((inputParam, index) => (
            <NodeInputHandler key={index} inputParam={inputParam} data={data}/>
          ))}

          {data.outputAnchors.length > 0 && (
            <>
              <Divider/>
              <Flex vertical style={{p: 1}}>
                <Typography.Title level={5} style={{fontWeight: 500, textAlign: 'center'}}>
                  Output
                </Typography.Title>
              </Flex>
              <Divider/>
            </>
          )}
          {data.outputAnchors.length > 0 &&
            data.outputAnchors.map((outputAnchor) => (
              <NodeOutputHandler key={JSON.stringify(data)} outputAnchor={outputAnchor} data={data}/>
            ))}
        </Flex>
      </ProCard>
    </XFlow>
  );
}

register({
  shape: FLOWISE_NODE,
  width: 212,
  height: 48,
  component: FlowiseNode,
  effect: ['ports', 'data'],
  // port默认不可见. stroke 和 fill 设置为 transparent
  ports: {
    groups: {
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

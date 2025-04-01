import React from "react";
import {Flex, Image, Space, Typography} from "antd";
import {ProCard} from "@ant-design/pro-components";
import {getIntl, getLocale} from "@umijs/max";
import {Graph, Node, register, XFlow} from "@antv/xflow";
import moment from "moment";

const CICD_NODE = 'cicd-backup-node';
const CICD_EDGE = 'cicd-backup-edge';

const CICDNode = ({node}: { node: Node }) => {
  const intl = getIntl(getLocale())
  const data = node?.getData()
  const {label, meta, attrs, extData} = data;
  const {startTime, endTime, status, outputs, tasks = []} = extData;

  return (
    <XFlow>
      <ProCard
        title={label}
        size={'small'}
        style={{
          width: 240
        }}
        headStyle={{
          overflow: 'hidden',
          whiteSpace: 'nowrap',
          textOverflow: 'ellipsis',
        }}
      >
        <ProCard title={(
          <Space>
            <Image src={meta.icon} alt={meta.type} sytle={{width: 12, height: 12}} preview={false}/>
            {meta.label}
          </Space>)}
                 subTitle={moment(endTime).diff(moment(startTime), 'seconds') + '秒'}
                 type={"inner"}
                 size={'small'}
                 bordered
                 headStyle={{
                   overflow: 'hidden',
                   whiteSpace: 'nowrap',
                   textOverflow: 'ellipsis',
                 }}
        >
          {tasks.length > 0 && (
            <Flex vertical gap={"small"}>
              {tasks.map((item, index) => (
                <ProCard title={(
                  <Flex gap={"small"}>
                    <Image src={meta.icon} alt={meta.type} sytle={{width: 12, height: 12}} preview={false}/>
                    <Typography.Text ellipsis={{ tooltip: item.name }}>
                      {item.name}
                    </Typography.Text>
                  </Flex>
                )}
                         size={'small'}
                         bordered
                         hoverable
                         colSpan={{xs: 24, sm: 12, md: 12, lg: 12, xl: 12}}
                         layout="center"
                >
                  {moment(item.endTime).diff(moment(item.startTime), 'seconds') + '秒'}
                </ProCard>
              ))}
            </Flex>
          )}
        </ProCard>
      </ProCard>
    </XFlow>
  );
}

register({
  shape: CICD_NODE,
  width: 240,
  height: 125,
  component: CICDNode,
  // 不可拖拽
  draggable: true,
  // port默认不可见
  ports: {
    groups: {
      in: {
        position: {
          name: 'left',
          args: {
            dx: 4,
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

      out: {
        position: {
          name: 'right',
          args: {
            dx: -4,
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
    connector: 'smooth',
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

export {CICD_NODE, CICD_EDGE};

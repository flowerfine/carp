import React from 'react';
import {Col, Image, Popover, Row} from "antd";
import {CopyOutlined, DashOutlined, DeleteOutlined, EditOutlined, PlayCircleOutlined} from "@ant-design/icons";
import {Graph, Node, register} from "@antv/xflow";
import {Dropdown, Menu} from '@antv/x6-react-components';
import {useStyles} from './style';

const {Item: MenuItem, Divider} = Menu;

const BASIC_NODE = 'basic-node';
const BASIC_EDGE = 'basic-edge';

const BasicNode: React.FC = ({node}: { node: Node }) => {
  const {styles, cx} = useStyles();
  const {label, dndMeta} = node?.getData()

  const onMenuItemClick = (key: string) => {
    const graph = node?.model?.graph;
    if (!graph) {
      return;
    }
    switch (key) {
      case 'delete':
        node.remove();
        break;
      case 'exec':
        node.setData({
          ...node.data,
          status: 'running',
        });
        setTimeout(() => {
          node.setData({
            ...node.data,
            status: 'success',
          });
        }, 2000);
        break;
      case 'copy':
        graph.copy([graph.getCellById(node.id)]);
        break;
      case 'paste':
        graph.paste();
        break;
      case 'rename':
        setOpen(true);
        break;
      default:
        break;
    }
  };

  const menu = (
    <Menu hasIcon={true} onClick={(key: string) => onMenuItemClick(key)}>
      <MenuItem name="rename" icon={<EditOutlined/>} text="重命名"/>
      <MenuItem name="copy" icon={<CopyOutlined/>} text="复制"/>
      <MenuItem name="delete" icon={<DeleteOutlined/>} text="删除"/>
    </Menu>
  );

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
    <div className={cx(styles.nodeWrap)} onMouseEnter={onMainMouseEnter} onMouseLeave={onMainMouseLeave}>
      <Col style={{width: '212px', height: '25px'}}>
        <Row justify="space-around" align="middle">
          <Col span={4}>
            <Image src={dndMeta?.icon} alt={dndMeta?.type} preview={false}/>
          </Col>
          <Col span={20}>
            <Row align="middle">
              <Col span={18}>{label}</Col>
              <Col span={3}>
                <Popover content={"执行节点"}>
                  <PlayCircleOutlined/>
                </Popover>
              </Col>
              <Col span={3}>
                <Dropdown overlay={menu}>
                  <DashOutlined/>
                </Dropdown>
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
    </div>
  );
};

register({
  shape: BASIC_NODE,
  // 需与自定义节点大小一致
  width: 240,
  height: 60,
  component: BasicNode,
  // port默认不可见. stroke 和 fill 设置为 transparent
  ports: {
    groups: {
      out: {
        position: 'right',
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
    },
  },
})

Graph.registerEdge(
  BASIC_EDGE,
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
  },
  true)

export {BASIC_NODE, BASIC_EDGE, BasicNode};

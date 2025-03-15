import React, {useState} from 'react';
import {Button, Col, Flex, Image, Popover, Row} from "antd";
import {
  CopyOutlined,
  DashOutlined,
  DeleteOutlined,
  EditOutlined,
  PlayCircleOutlined,
  ScissorOutlined
} from "@ant-design/icons";
import {getIntl, getLocale} from "@umijs/max";
import {Node} from "@antv/xflow";
import {Dropdown, Menu} from '@antv/x6-react-components';
import {ControlInput} from "@/components/Input/ControlInput";
import {useStyles} from './style';

const {Item: MenuItem} = Menu;

type BasicNodeProps = {
  node: Node,
  children?: React.ReactNode
}

const BasicNode: React.FC = ({node, children}: BasicNodeProps) => {
  const intl = getIntl(getLocale())
  const {styles, cx} = useStyles();
  const {label, dndMeta} = node?.getData()
  const [labelEdited, setLabelEdited] = useState(false);

  const onLabelEdit = (value: string) => {
    node.setData({...node.data, label: value});
  }

  const onMenuItemClick = (key: string) => {
    const graph = node?.model?.graph;
    if (!graph) {
      return;
    }
    switch (key) {
      case 'cut':
        graph.cut([graph.getCellById(node.id)]);
        break;
      case 'copy':
        graph.copy([graph.getCellById(node.id)]);
        graph.paste();
        break;
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
      default:
        break;
    }
  };

  const menu = (
    <Menu hasIcon={true} onClick={(key: string) => onMenuItemClick(key)}>
      {/*<MenuItem name="cut" icon={<ScissorOutlined />} hotkey="Command ⌘ + X" text={intl.formatMessage({ id: 'app.common.operate.cut.label' })} />*/}
      <MenuItem name="copy" icon={<CopyOutlined/>} hotkey="Command ⌘ + C" text={intl.formatMessage({ id: 'app.common.operate.copy.label' })}/>
      <MenuItem name="delete" icon={<DeleteOutlined/>} hotkey="Delete" text={intl.formatMessage({ id: 'app.common.operate.delete.label' })}/>
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
      <Col style={{width: '212px'}}>
        <Row justify="space-around" align="middle">
          <Col span={4}>
            <Image src={dndMeta?.icon} alt={dndMeta?.type} preview={false}/>
          </Col>
          <Col span={20}>
            <Row align="middle">
              <Col span={18}>
                {labelEdited ?
                  (
                    <ControlInput
                      onChange={onLabelEdit}
                      onChangeEnd={() => setLabelEdited(false)}
                      value={label}
                    />
                  )
                  : (<Flex gap={8} align={'center'} vertical={false}>
                    {label}
                    <Button icon={<EditOutlined/>} type="text" onClick={() => setLabelEdited(true)}/>
                  </Flex>)}
              </Col>
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
        {children && (
          <Row>
            <Col>{children}</Col>
          </Row>
        )}
      </Col>
    </div>
  );
};

export default BasicNode;

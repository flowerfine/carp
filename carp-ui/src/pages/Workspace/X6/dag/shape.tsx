import {useEffect, useState} from 'react';
import {Input, Modal} from 'antd';
import {CopyOutlined, DeleteOutlined, EditOutlined, PlayCircleOutlined,} from '@ant-design/icons';
import type {Node} from '@antv/xflow';
import {Graph, register, XFlow} from '@antv/xflow';
import {Dropdown, Menu} from '@antv/x6-react-components';
import '@antv/x6-react-components/dist/index.css';
import './node.less';

const {Item: MenuItem, Divider} = Menu;

const DAG_NODE = 'dag-node';
const DAG_EDGE = 'dag-edge';

interface NodeStatus {
  id: string;
  status: 'default' | 'success' | 'failed' | 'running';
  label?: string;
}

const image = {
  logo: 'https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*evDjT5vjkX0AAAAAAAAAAAAAARQnAQ',
  success:
    'https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*6l60T6h8TTQAAAAAAAAAAAAAARQnAQ',
  failed:
    'https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*SEISQ6My-HoAAAAAAAAAAAAAARQnAQ',
  running:
    'https://gw.alipayobjects.com/mdn/rms_43231b/afts/img/A*t8fURKfgSOgAAAAAAAAAAAAAARQnAQ',
};

const AlgoNode = ({node}: { node: Node }) => {
  const data = node?.getData() as NodeStatus;
  const {label, status = 'default'} = data || {};
  const [open, setOpen] = useState(false);
  const [value, setValue] = useState<string | undefined>();

  useEffect(() => {
    setValue(label);
  }, [label]);

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
      <MenuItem name="paste" icon={<CopyOutlined/>} text="粘贴"/>
      <MenuItem name="delete" icon={<DeleteOutlined/>} text="删除"/>
      <Divider/>
      <MenuItem name="exec" icon={<PlayCircleOutlined/>} text="执行节点"/>
    </Menu>
  );

  return (
    <XFlow>
      <Modal
        title="重命名"
        open={open}
        okText="确定"
        cancelText="取消"
        onCancel={() => setOpen(false)}
        onOk={() => {
          node.setData({
            ...node.data,
            label: value,
          });
          setOpen(false);
        }}
      >
        <Input value={value} onChange={(e) => setValue(e.target.value)}/>
      </Modal>
      <Dropdown
        overlay={menu}
        trigger={['contextMenu']}
        overlayStyle={{overflowY: 'auto'}}
      >
        <div className={`node ${status}`}>
          <img src={image.logo} alt="logo"/>
          <span className="label">{label}</span>
          <span className="status">
            {status === 'success' && <img src={image.success} alt="success"/>}
            {status === 'failed' && <img src={image.failed} alt="failed"/>}
            {status === 'running' && <img src={image.running} alt="running"/>}
          </span>
        </div>
      </Dropdown>
    </XFlow>
  );
};

register({
  shape: DAG_NODE,
  width: 180,
  height: 36,
  component: AlgoNode,
  effect: ['data'],
  ports: {
    groups: {
      top: {
        position: 'top',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#C2C8D5',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },
      bottom: {
        position: 'bottom',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#C2C8D5',
            strokeWidth: 1,
            fill: '#fff',
          },
        },
      },
    },
  },
});

Graph.registerEdge(
  DAG_EDGE,
  {
    inherit: 'edge',
    attrs: {
      line: {
        stroke: '#C2C8D5',
        strokeWidth: 1,
        targetMarker: null,
      },
    },
    zIndex: -1,
  },
  true,
);

export {DAG_NODE, DAG_EDGE};

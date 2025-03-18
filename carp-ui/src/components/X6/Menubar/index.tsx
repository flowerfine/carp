import React, {useState} from 'react';
import {Button, Flex, Space, Tooltip} from "antd";
import {DownloadOutlined, EditOutlined, LeftOutlined, SaveOutlined, UploadOutlined} from "@ant-design/icons";
import {history} from '@umijs/max';
import {Edge, useGraphInstance, useGraphStore} from "@antv/xflow";
import {Menubar} from "@antv/x6-react-components";
import {EdgeOptions} from "@antv/xflow/src/types";
import {ControlInput} from "@/components/Input/ControlInput";

type X6MenubarProps = {
  data?: any;
  name: string;
  onNameChange?: (name: string) => void;
  onSave?: (data: any, graph: any) => void;
};

const X6Menubar: React.FC = ({data, name, onNameChange, onSave}: X6MenubarProps) => {
  const graph = useGraphInstance();
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const [nameEdited, setNameEdited] = useState(false);

  const buildGraphData = () => {
    const concatEdges: EdgeOptions[] = nodes.flatMap((node) => {
      let result: Edge[] = []
      const incomingEdges = graph?.getIncomingEdges(node.id || '');
      if (incomingEdges) {
        result = result.concat(incomingEdges)
      }
      const outgoingEdges = graph?.getOutgoingEdges(node.id || '');
      if (outgoingEdges) {
        result = result.concat(outgoingEdges)
      }
      // 后续需去重
      return result.map(edge => edges.find(item => edge.id === item.id))
    });

    return  {
      nodes: nodes.map(node => {
        return {
          id: node.id,
          shade: node.shape,
          position: node.position,
          ports: node.ports?.items,
          data: node.data
        }
      }),
      edges: unique(concatEdges).map(edge => {
        return {
          id: edge.id,
          shape: edge.shape,
          source: edge.source,
          target: edge.target,
          data: edge.data
        }
      })
    }
  };

  const onSaveClicked = () => {
    onSave(data, buildGraphData())
  };

  function unique(arr: any[]) {
    return arr.filter(function(item, index, arr) {
      //当前元素，在原始数组中的第一个索引==当前索引值，否则返回当前元素
      return arr.indexOf(item, 0) === index;
    });
  }

  const enableNameChange = () => {
    return nameEdited ?
      (
        <ControlInput
          onChange={onNameChange}
          onChangeEnd={() => setNameEdited(false)}
          value={name}
        />
      )
      :
      (<Flex gap={8} align={'center'} vertical={false}>
        {name}
        <Button icon={<EditOutlined/>} type="text" onClick={() => setNameEdited(true)}/>
      </Flex>)
  }

  return (
    <Menubar
      extra={
        <Space>
          <Space.Compact>
            <Tooltip title={"导入"}>
              <Button icon={<UploadOutlined/>} type="text"/>
            </Tooltip>
            <Tooltip title={"导出"}>
              <Button icon={<DownloadOutlined/>} type="text"/>
            </Tooltip>
          </Space.Compact>
          <Button icon={<SaveOutlined/>} type="primary" onClick={onSaveClicked}>保存</Button>
        </Space>
      }>
      <Space>
        <Tooltip title={"返回"}>
          <Button icon={<LeftOutlined/>} onClick={() => history.back()}/>
        </Tooltip>
        {onNameChange ?
          (enableNameChange())
          : (<div>{name}</div>)
        }
      </Space>
    </Menubar>
  );
};

export default X6Menubar;

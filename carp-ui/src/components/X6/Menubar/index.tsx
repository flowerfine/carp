import React, {useEffect, useState} from 'react';
import {Button, Flex, message, Space, Tooltip, Upload, UploadProps} from "antd";
import {
  DownloadOutlined,
  EditOutlined,
  LeftOutlined,
  PlayCircleOutlined,
  SaveOutlined,
  UploadOutlined
} from "@ant-design/icons";
import {getIntl, getLocale, history} from '@umijs/max';
import {Edge, useGraphInstance, useGraphStore} from "@antv/xflow";
import {Menubar} from "@antv/x6-react-components";
import {EdgeOptions} from "@antv/xflow/src/types";
import {ControlInput} from "@/components/Input/ControlInput";

type X6MenubarProps = {
  data?: any;
  name: string;
  onNameChange?: (name: string) => void;
  onSave?: (data: any, graph: X6API.Graph) => void;
  onExecute?: (data: any, graph: X6API.Graph) => void;
};

const X6Menubar: React.FC = ({data, name, onNameChange, onSave, onExecute}: X6MenubarProps) => {
  const intl = getIntl(getLocale())
  const graph = useGraphInstance();
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const [dagName, setDagName] = useState<string>();
  const [nameEdited, setNameEdited] = useState(false);

  useEffect(() => {
    setDagName(name)
  }, []);

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

    const graph: X6API.Graph = {
      nodes: nodes.map(node => {
        return {
          id: node.id,
          shape: node.shape,
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
    return graph;
  };

  const onExportClicked = () => {
    try {
      let dataStr = JSON.stringify(buildGraphData(), null, 2)
      const blob = new Blob([dataStr], {type: 'application/json'})
      const dataUri = URL.createObjectURL(blob)

      let exportFileDefaultName = `${name}.json`

      let linkElement = document.createElement('a')
      linkElement.setAttribute('href', dataUri)
      linkElement.setAttribute('download', exportFileDefaultName)
      linkElement.click()
    } catch (e) {
      console.error(e)
    }
  }

  const onSaveClicked = () => {
    onSave(data, buildGraphData())
  };

  const onExecuteClicked = () => {
    onExecute(data, buildGraphData())
  };

  function unique(arr: any[]) {
    return arr.filter(function (item, index, arr) {
      //当前元素，在原始数组中的第一个索引==当前索引值，否则返回当前元素
      return arr.indexOf(item, 0) === index;
    });
  }

  const props: UploadProps = {
    name: 'file',
    showUploadList: false,
    beforeUpload: (file) => {
      const isJson = file.type === 'application/json';
      if (!isJson) {
        message.error(`${file.name} is not a json`);
      }
      return isJson || Upload.LIST_IGNORE;
    },
    onChange(info) {
      if (info.file.status !== 'uploading') {
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} upload successfully`);
        fileUpoload(info.file.originFileObj)
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} upload failed`);
      }
    },
  };

  const fileUpoload = (file: any) => {
    const reader = new FileReader()
    reader.onload = (evt) => {
      if (!evt?.target?.result) {
        return
      }
      const {result} = evt.target
      let {nodes, edges} = JSON.parse(result) as X6API.Graph;
      if (nodes) {
        graph.addNodes(nodes);
      }
      if (edges) {
        graph.addEdges(edges);
      }
    }
    reader.readAsText(file)
  }

  const enableNameChange = () => {
    return nameEdited ?
      (
        <ControlInput
          onChange={(name) => {
            onNameChange(name)
            setDagName(name)
          }}
          onChangeEnd={() => setNameEdited(false)}
          value={dagName}
        />
      )
      :
      (<Flex gap={8} align={'center'} vertical={false}>
        {dagName}
        <Button icon={<EditOutlined/>} type="text" onClick={() => setNameEdited(true)}/>
      </Flex>)
  }

  return (
    <Menubar
      extra={
        <Space>
          <Space.Compact>
            <Tooltip title={intl.formatMessage({id: 'app.common.operate.import.label'})}>
              <Upload {...props}>
                <Button icon={<UploadOutlined/>} type="text"/>
              </Upload>
            </Tooltip>
            <Tooltip title={intl.formatMessage({id: 'app.common.operate.export.label'})}>
              <Button icon={<DownloadOutlined/>} type="text" onClick={onExportClicked}/>
            </Tooltip>
            {onSave && (
              <Tooltip title={intl.formatMessage({id: 'app.common.operate.save.label'})}>
                <Button icon={<SaveOutlined/>} type="text" onClick={onSaveClicked}/>
              </Tooltip>)}
          </Space.Compact>
          {onExecute && (
            <Button icon={<PlayCircleOutlined/>} type="primary" onClick={onExecuteClicked}>
              {intl.formatMessage({id: 'app.common.operate.exec.label'})}
            </Button>
          )}
        </Space>
      }>
      <Space>
        <Tooltip title={intl.formatMessage({id: 'app.common.operate.return.label'})}>
          <Button icon={<LeftOutlined/>} onClick={() => history.back()}/>
        </Tooltip>
        {onNameChange ?
          (enableNameChange())
          : (<div>{dagName}</div>)
        }
      </Space>
    </Menubar>
  );
};

export default X6Menubar;

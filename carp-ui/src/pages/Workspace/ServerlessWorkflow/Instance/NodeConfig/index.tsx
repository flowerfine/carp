import React, {useState} from "react";
import {Node, useGraphEvent} from "@antv/xflow";
import ServerlessNodeHttpForm from "@/pages/Workspace/ServerlessWorkflow/Instance/NodeConfig/nodes/http";

const ServerlessNodeConfig: React.FC = () => {
  const [open, setOpen] = useState(false);
  const [nodeObj, setNodeObj] = useState<Node>();

  useGraphEvent('node:dblclick', ({node}) => {
    setNodeObj(node);
    setOpen(true);
  });

  useGraphEvent('blank:click', () => {
    setOpen(false);
  });

  const onOk = (values: Record<string, any>) => {
    // 移除 undefined 字段，否则会更新异常
    const attrs: Record<string, any> = Object.keys(values)
      .filter((key) => values[key] != null && values[key] != undefined)
      .reduce((acc, key) => ({...acc, [key]: values[key]}), {});
    nodeObj?.setData({...nodeObj.data, nodeData: attrs})
    setOpen(false);
  };

  const switchStep = () => {
    if (!nodeObj) {
      return (<></>);
    }

    if (nodeObj?.data?.dndMeta?.type === 'http') {
      return (<ServerlessNodeHttpForm data={nodeObj}
                                      visible={open}
                                      onVisibleChange={setOpen}
                                      onCancel={() => setOpen(false)}
                                      onFinish={onOk}
      />)
    }

    return (<></>);
  }

  return (<div>{switchStep()}</div>);
};

export default ServerlessNodeConfig;

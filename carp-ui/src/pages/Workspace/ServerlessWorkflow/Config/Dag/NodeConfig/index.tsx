import React from "react";
import {Node} from "@antv/xflow";
import ServerlessNodeHttpForm from "@/pages/Workspace/ServerlessWorkflow/Instance/NodeConfig/nodes/http";
import {ModalFormProps} from "@/typings";

const ServerlessNodeConfig: React.FC<ModalFormProps<Node>> = ({visible, onCancel, onFinish, data}) => {

  const switchStep = () => {
    if (data?.data?.meta?.type === 'http') {
      return (<ServerlessNodeHttpForm data={data} visible={visible} onCancel={onCancel} onFinish={onFinish}/>)
    }
    return (<></>);
  }

  return (<div>{switchStep()}</div>);
};

export default ServerlessNodeConfig;

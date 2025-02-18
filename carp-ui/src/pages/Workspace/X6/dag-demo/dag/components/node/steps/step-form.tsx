import {Node} from "@antv/xflow";
import {ModalFormProps} from "@/typings";

const SeaTunnnelConnectorForm: React.FC<ModalFormProps<Node>> = ({visible, onVisibleChange, onCancel, onOK, data}) => {

  const switchStep = (node: Node) => {
    const name = node.data.meta.name;
    const type = node.data.meta.type;
    return <></>;
  };

  return (<div>{switchStep(data)}</div>);
}

export default SeaTunnnelConnectorForm;

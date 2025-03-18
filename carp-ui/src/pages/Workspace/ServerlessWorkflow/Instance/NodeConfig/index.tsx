import React, {useState} from "react";
import {Form} from "antd";
import {DrawerForm, ProFormText} from "@ant-design/pro-components";
import {useIntl} from "@umijs/max";
import {useGraphEvent, useGraphInstance} from "@antv/xflow";

interface NodeData {
  id: string;
  label?: string;
  dndMeta?: any;
}

const ServerlessNodeConfig: React.FC = () => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const graph = useGraphInstance();
  const [open, setOpen] = useState(false);
  const [nodeData, setNodeData] = useState<NodeData>();

  useGraphEvent('node:dblclick', ({node}) => {
    const {id, data} = node;
    setOpen(true);
    setNodeData({...data, id});
    form.setFieldsValue(data);
  });

  useGraphEvent('blank:click', () => {
    onClose();
  });

  const onClose = () => {
    setOpen(false);
    form.resetFields();
  };

  return (
    <DrawerForm
      title={nodeData?.label}
      open={open}
      grid={true}
      width={780}
      form={form}
      drawerProps={{
        styles: {body: {overflowY: 'scroll'}},
        closeIcon: null,
        destroyOnClose: true,
        mask: false
      }}
    >
      <ProFormText
        name={"name"}
        label={"组件名"}
        placeholder={"请输入组件名称"}
        rules={[{required: true, message: '请填写组件名称'}]}
      />
    </DrawerForm>
  );
};

export default ServerlessNodeConfig;

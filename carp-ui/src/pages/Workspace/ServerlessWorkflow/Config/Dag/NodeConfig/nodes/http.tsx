import React from "react";
import {Form} from "antd";
import {DrawerForm, ProFormSelect, ProFormText} from "@ant-design/pro-components";
import {getIntl, getLocale} from "@umijs/max";
import {Node} from "@antv/xflow";
import {ModalFormProps} from "@/typings";

const ServerlessNodeHttpForm: React.FC<ModalFormProps<Node>> = ({data, visible, onVisibleChange, onCancel, onFinish}) => {
  const intl = getIntl(getLocale());
  const [form] = Form.useForm();
  const nodeDataObj = data?.data;

  return (
    <DrawerForm
      title={nodeDataObj?.label}
      open={visible}
      onOpenChange={onVisibleChange}
      grid={true}
      width={780}
      form={form}
      initialValues={nodeDataObj?.nodeData}
      drawerProps={{
        styles: {body: {overflowY: 'scroll'}},
        onClose: (e) => onCancel(),
        closeIcon: null,
        destroyOnClose: true,
        mask: false
      }}
      onFinish={(values) => {
        if (onFinish) {
          onFinish(values)
          return Promise.resolve(true)
        }
        return Promise.resolve(false)
      }}
    >
      <ProFormSelect
        name={"method"}
        label={"方法"}
        rules={[{required: true}]}
        allowClear={false}
        options={['get']}
      />
      <ProFormText
        name={"endpoint"}
        label={"Endpoint"}
        rules={[{required: true}]}
      />
    </DrawerForm>
  );
};

export default ServerlessNodeHttpForm;

import React from 'react';
import {Form, message} from 'antd';
import {
  ModalForm,
  ProFormDependency,
  ProFormDigit,
  ProFormSelect,
  ProFormText,
  ProFormTextArea
} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import { CepRuleService } from '@/services/workspace/cep/cep-rule.service';

export default (props: ModalFormProps<WorkspaceCepAPI.CepRule>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceCepAPI.CepRule>
      title={
        data?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule' })
      }
      layout={"horizontal"}
      labelCol={{ span: 6 }}
      wrapperCol={{ span: 16 }}
      labelAlign={'right'}
      width={"500px"}
      modalProps={{
        destroyOnClose: true,
        maskClosable: true,
        onCancel: onCancel
      }}
      form={form}
      scrollToFirstError={true}
      preserve={false}
      open={visible}
      initialValues={{
        id: data?.id,
        namespace: data?.namespace,
        name: data?.name,
        window: data?.window,
        function: data?.function,
        remark: data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = { ...values };
        return data?.id
          ? CepRuleService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : CepRuleService.add(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.new.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
      }}
    >
      <ProFormDigit name="id" hidden />
      <ProFormText
        name="namespace"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.cep.namespace' })}
        rules={[{ required: true }, { max: 32 }]}
        allowClear={false}
        disabled={data?.id ? true : false}
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.name' })}
        rules={[{ required: true }, { max: 32 }]}
        allowClear={false}
      />
      <ProFormText
        name="window"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.window' })}
      />
      <ProFormText
        name="function"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.cep.rule.function' })}
        rules={[{ required: true }]}
      />
     
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

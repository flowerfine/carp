import React from 'react';
import {Form, message} from 'antd';
import {
  ModalForm,
  ProFormDigit,
  ProFormText,
  ProFormTextArea
} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import { WorkspaceCepAPI } from '@/services/workspace/cep/typings';
import { CepWorkflowService } from '@/services/workspace/cep/cep-workflow.service';

export default (props: ModalFormProps<WorkspaceCepAPI.CepWorkflow>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceCepAPI.CepWorkflow>
      title={
        data?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow' })
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
        remark: data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = { ...values };
        return data?.id
          ? CepWorkflowService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : CepWorkflowService.add(param).then((response) => {
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
        label={intl.formatMessage({ id: 'pages.workspace.workflow.cep.workflow.name' })}
        rules={[{ required: true }, { max: 32 }]}
        allowClear={false}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

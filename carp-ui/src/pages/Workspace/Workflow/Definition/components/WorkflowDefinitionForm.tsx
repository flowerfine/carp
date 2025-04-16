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
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";

export default (props: ModalFormProps<{ namespace: string, workflowDefinition?: WorkspaceWorkflowAPI.WorkflowDefinition }>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceWorkflowAPI.WorkflowDefinition>
      title={
        data?.workflowDefinition?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.definition' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.definition' })
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
        id: data?.workflowDefinition?.id,
        namespace: data?.namespace,
        name: data?.workflowDefinition?.name,
        engine: data?.workflowDefinition?.engine.value,
        body: data?.workflowDefinition?.body,
        remark: data?.workflowDefinition?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = { ...values };
        return data?.workflowDefinition?.id
          ? WorkflowDefinitionService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : WorkflowDefinitionService.add(param).then((response) => {
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
        label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.namespace' })}
        hidden
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.name' })}
        rules={[{ required: true }, { max: 32 }]}
        allowClear={false}
      />
      <ProFormSelect
        name="engine"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.engine' })}
        rules={[{ required: true }]}
        disabled={data?.workflowDefinition?.id ? true : false}
        allowClear={false}
        request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpWorkflowEngineType)}
      />
      <ProFormDependency name={['engine']}>
        {({ engine }) => {
          if (engine === 'temporal') {
            return (
              <ProFormText
                name={["body", "workflowMethod"]}
                label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.engine.temporal.workflowMethod' })}
                tooltip={intl.formatMessage({ id: 'pages.workspace.workflow.definition.engine.temporal.workflowMethod.tooltip' })}
                rules={[{ required: true }]}
              />
            )
          }
          return <></>
        }}
      </ProFormDependency>
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

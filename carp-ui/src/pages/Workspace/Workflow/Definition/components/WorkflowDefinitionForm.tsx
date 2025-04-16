import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowDefinitionService} from "@/services/workspace/workflow/workflow-definition.service";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";

export default (props: ModalFormProps<WorkspaceWorkflowAPI.WorkflowDefinition>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, onCancel, onFinish} = props;

  return (
    <ModalForm<WorkspaceWorkflowAPI.WorkflowDefinition>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.workspace.workflow.definition'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.workspace.workflow.definition'})
      }
      layout={"horizontal"}
      labelCol={{span: 6}}
      wrapperCol={{span: 16}}
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
        name: data?.name,
        engine: data?.engine.value,
        remark: data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = {
          id: values.id,
          namespace: "default",
          name: values.name,
          engine: values.engine,
          remark: values.remark
        };
        return data?.id
          ? WorkflowDefinitionService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : WorkflowDefinitionService.add(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.new.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
      }}
    >
      <ProFormDigit name="id" hidden/>
      <ProFormText
        name="name"
        label={intl.formatMessage({id: 'pages.workspace.workflow.definition.name'})}
        rules={[{required: true}, {max: 32}]}
      />
      <ProFormSelect
        name="engineType"
        label={intl.formatMessage({id: 'pages.workspace.workflow.definition.engine'})}
        rules={[{required: true}]}
        allowClear={false}
        request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpWorkflowEngineType)}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({id: 'app.common.data.remark'})}
        rules={[{max: 200}]}
      />
    </ModalForm>
  );
};

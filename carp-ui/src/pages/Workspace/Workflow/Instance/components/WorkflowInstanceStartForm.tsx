import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {WorkflowInstanceService} from '@/services/workspace/workflow/workflow-instance.service';

export default (props: ModalFormProps<WorkspaceWorkflowAPI.WorkflowInstance>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, onCancel, onFinish} = props;

  return (
    <ModalForm<WorkspaceWorkflowAPI.WorkflowInstance>
      title={
        intl.formatMessage({id: 'app.common.operate.start.label'}) +
        intl.formatMessage({id: 'pages.workspace.workflow.instance'})
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
        inputs: "{}",
        variables: "{}"
      }}
      onFinish={async (values: Record<string, any>) => {
        return WorkflowInstanceService.start({...values}).then((response) => {
          if (response.success) {
            message.success(intl.formatMessage({id: 'app.common.operate.start.success'}));
            if (onFinish) {
              onFinish(values);
            }
          }
        })
      }}
    >
      <ProFormDigit name="id" hidden/>
      <ProFormTextArea
        name="inputs"
        label={intl.formatMessage({id: 'pages.workspace.workflow.instance.start.inputs'})}
      />
      <ProFormTextArea
        name="variables"
        label={intl.formatMessage({id: 'pages.workspace.workflow.instance.start.variables'})}
      />
    </ModalForm>
  );
};

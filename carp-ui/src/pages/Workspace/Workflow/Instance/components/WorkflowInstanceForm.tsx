import React from 'react';
import {Form, message} from 'antd';
import {
  ModalForm,
  ProFormDateRangePicker,
  ProFormDependency,
  ProFormDigit,
  ProFormSelect,
  ProFormText,
  ProFormTextArea
} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {WorkspaceWorkflowAPI} from "@/services/workspace/workflow/typings";
import {DictService} from "@/services/admin/system/dict.service";
import {DICT_TYPE} from "@/constants/dictType";
import {WorkflowInstanceService} from '@/services/workspace/workflow/workflow-instance.service';

export default (props: ModalFormProps<{ workflowDefinition: WorkspaceWorkflowAPI.WorkflowDefinition, workflowInstance?: WorkspaceWorkflowAPI.WorkflowInstance }>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceWorkflowAPI.WorkflowInstance>
      title={
        data?.workflowInstance?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.instance' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.workflow.instance' })
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
        id: data?.workflowInstance?.id,
        namespace: data?.workflowDefinition.namespace,
        name: data?.workflowDefinition.name,
        engine: data?.workflowDefinition.engine.value,
        workflowDefinitionId: data?.workflowDefinition.id,
        params: data?.workflowInstance?.params,
        remark: data?.workflowInstance?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = { ...values };
        return data?.workflowInstance?.id
          ? WorkflowInstanceService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : WorkflowInstanceService.add(param).then((response) => {
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
        name="workflowDefinitionId"
        hidden
      />
      <ProFormText
        name="namespace"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.namespace' })}
        hidden
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.name' })}
        rules={[{ required: true }, { max: 32 }]}
        disabled={true}
        allowClear={false}
      />
      <ProFormSelect
        name="engine"
        label={intl.formatMessage({ id: 'pages.workspace.workflow.definition.engine' })}
        rules={[{ required: true }]}
        disabled={true}
        request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpWorkflowEngineType)}
      />
      <ProFormDependency name={['engine']}>
        {({ engine }) => {
          if (engine === 'temporal') {
            return (
              <>
                <ProFormText
                  name={["params", "queue"]}
                  label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.queue' })}
                  rules={[{ required: true }]}
                />
                <ProFormText
                  name={["params", "timezone"]}
                  label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.timezone' })}
                />
                <ProFormText
                  name={["params", "expression"]}
                  label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.expression' })}
                />
                <ProFormDateRangePicker
                  name={["params", "validTime"]}
                  label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.engine.temporal.validTime' })}
                  rules={[{ required: true }]}
                  fieldProps={{
                    showTime: true,
                    format: "YYYY-MM-DD HH:mm:ss",
                    id: {
                      start: "startTime",
                      end: "endTime"
                    }
                  }}
                />
              </>
            )
          }
          return <></>
        }}
      </ProFormDependency>
      <ProFormTextArea
        name={["params", "param"]}
        label={intl.formatMessage({ id: 'pages.workspace.workflow.instance.params.param' })}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";
import {ScheduleGroupService} from "@/services/workspace/schedule/group.service";

export default (props: ModalFormProps<WorkspaceScheduleAPI.ScheduleGroup>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, onCancel, onFinish} = props;

  return (
    <ModalForm<WorkspaceScheduleAPI.ScheduleGroup>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.workspace.schedule.group'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.workspace.schedule.group'})
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
        namespace: data?.namespace,
        name: data?.name,
        remark: data?.remark,
      }}
      onFinish={async (values: WorkspaceScheduleAPI.ScheduleGroup) => {
        const param = {
          id: values.id,
          namespace: values.namespace,
          name: values.name,
          remark: values.remark
        };
        return data?.id
          ? ScheduleGroupService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : ScheduleGroupService.add(param).then((response) => {
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
        name="namespace"
        label={intl.formatMessage({id: 'pages.workspace.schedule.group.namespace'})}
        rules={[{required: true}, {max: 32}]}
        disabled={data?.id}
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({id: 'pages.workspace.schedule.group.name'})}
        rules={[{required: true}, {max: 32}]}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({id: 'app.common.data.remark'})}
        rules={[{max: 200}]}
      />
    </ModalForm>
  );
};

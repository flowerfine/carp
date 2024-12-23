import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";
import {ScheduleConfigService} from '@/services/workspace/schedule/config.service';
import { ScheduleGroupService } from '@/services/workspace/schedule/group.service';

export default (props: ModalFormProps<WorkspaceScheduleAPI.ScheduleConfig>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceScheduleAPI.ScheduleConfig>
      title={
        data?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.schedule.config' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.schedule.config' })
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
        jobGroupId: data?.jobGroup?.id,
        type: data?.type.value,
        name: data?.name,
        engineType: data?.engineType.value,
        jobType: data?.jobType.value,
        handler: data?.handler,
        remark: data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const param = {
          id: values.id,
          jobGroupId: values.jobGroupId,
          type: values.type,
          name: values.name,
          engineType: values.engineType,
          jobType: values.jobType,
          handler: values.handler,
          remark: values.remark
        };
        return data?.id
          ? ScheduleConfigService.update(param).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : ScheduleConfigService.add(param).then((response) => {
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
      <ProFormSelect
        name="jobGroupId"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.jobGroup' })}
        rules={[{ required: true }]}
        disabled={data?.id ? true : false}
        request={((params, props) => {
          const param = {
            name: params.keyWords,
          }
          return ScheduleGroupService.list().then((response) => {
            if (response.data) {
              return response.data?.map((item) => {
                return {label: item.name, value: item.id, item: item};
              });
            }
            return []
          });
        })}
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.name' })}
        rules={[{ required: true }, { max: 32 }]}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

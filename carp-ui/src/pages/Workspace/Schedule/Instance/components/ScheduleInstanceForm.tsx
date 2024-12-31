import React from 'react';
import { Form, message } from 'antd';
import { ModalForm, ProFormDateRangePicker, ProFormDigit, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import dayjs from 'dayjs';
import { ModalFormProps } from "@/typings";
import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import { ScheduleInstanceState } from '..';
import { ScheduleInstanceService } from '@/services/workspace/schedule/instance.service';

export default (props: ModalFormProps<ScheduleInstanceState>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const { visible, data, onCancel, onFinish } = props;

  return (
    <ModalForm<WorkspaceScheduleAPI.ScheduleConfig>
      title={
        data?.data?.id
          ? intl.formatMessage({ id: 'app.common.operate.edit.label' }) +
          intl.formatMessage({ id: 'pages.workspace.schedule.instance' })
          : intl.formatMessage({ id: 'app.common.operate.new.label' }) +
          intl.formatMessage({ id: 'pages.workspace.schedule.instance' })
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
        id: data?.data?.id,
        jobConfigId: data?.jobConfigId,
        name: data?.data?.name,
        timezone: data?.data?.timezone,
        cron: data?.data?.cron,
        validTime: [dayjs(data?.data?.startTime, 'YYYY-MM-DD HH:mm:ss'), dayjs(data?.data?.endTime, 'YYYY-MM-DD HH:mm:ss')],
        timeout: data?.data?.timeout,
        params: data?.data?.params,
        remark: data?.data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        const params = {
          id: values.id,
          jobConfigId: values.jobConfigId,
          name: values.name,
          timezone: values.timezone,
          cron: values.cron,
          startTime: values.validTime[0],
          endTime: values.validTime[1],
          timeout: values.timeout,
          params: values.params,
          remark: values.remark,
      }
        return data?.data?.id
          ? ScheduleInstanceService.update(params).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({ id: 'app.common.operate.edit.success' }));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : ScheduleInstanceService.add(params).then((response) => {
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
      <ProFormDigit
        name="jobConfigId"
        hidden={true}
      />
      <ProFormText
        name="name"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.name' })}
        rules={[{ required: true }, { max: 32 }]}
      />
      <ProFormText
        name="timezone"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.timezone' })}
        rules={[{ required: true }]}
      />
      <ProFormText
        name="cron"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.cron' })}
        rules={[{ required: true }]}
      />
      <ProFormDateRangePicker
        name={"validTime"}
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.validTime' })}
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
      <ProFormText
        name="timeout"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.timeout' })}
      />
      <ProFormTextArea
        name="params"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.instance.params' })}
      />
      <ProFormTextArea
        name="remark"
        label={intl.formatMessage({ id: 'app.common.data.remark' })}
        rules={[{ max: 200 }]}
      />
    </ModalForm>
  );
};

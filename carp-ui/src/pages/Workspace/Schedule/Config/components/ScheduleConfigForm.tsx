import React from 'react';
import { Form, message } from 'antd';
import { ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { ModalFormProps } from "@/typings";
import { WorkspaceScheduleAPI } from "@/services/workspace/schedule/typings";
import { ScheduleConfigService } from '@/services/workspace/schedule/config.service';
import { ScheduleGroupService } from '@/services/workspace/schedule/group.service';
import { ScheduleExecutorService } from '@/services/workspace/schedule/executor.service';

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
        executeType: data?.executeType.value,
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
          executeType: values.executeType,
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
                return { label: item.name, value: item.id, item: item };
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
      <ProFormSelect
        name="engineType"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.engineType' })}
        rules={[{ required: true }]}
        allowClear={false}
        disabled={data?.id ? true : false}
        request={() => ScheduleExecutorService.getEngines().then(response => {
          if (response.data) {
            return response.data
          }
          return []
        })}
      />

      <ProFormSelect
        name="jobType"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.jobType' })}
        rules={[{ required: true }]}
        allowClear={false}
        disabled={data?.id ? true : false}
        dependencies={["engineType"]}
        request={(params, props) => {
          if (!params.engineType) {
            return Promise.reject()
          }
          return ScheduleExecutorService.getTypes(params.engineType).then(response => {
            if (response.data) {
              return response.data
            }
            return []
          })
        }}
      />
      <ProFormSelect
        name="executeType"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.executeType' })}
        rules={[{ required: true }]}
        allowClear={false}
        disabled={data?.id ? true : false}
        dependencies={["engineType", "jobType"]}
        request={(params, props) => {
          if (!params.engineType || !params.jobType) {
            return Promise.reject()
          }
          return ScheduleExecutorService.getExecutors(params.engineType, params.jobType).then(response => {
            if (response.data) {
              return response.data
            }
            return []
          })
        }}
      />
      <ProFormText
        name="handler"
        label={intl.formatMessage({ id: 'pages.workspace.schedule.config.handler' })}
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

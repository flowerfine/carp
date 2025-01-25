import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormSelect, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormProps} from "@/typings";
import {AdminSecurityAPI} from '@/services/admin/security/typings';
import {DictService} from '@/services/admin/system/dict.service';
import {DICT_TYPE} from '@/constants/dictType';
import {RoleService} from '@/services/admin/security/role.service';

export default (props: ModalFormProps<AdminSecurityAPI.SecRole>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, onCancel, onFinish} = props;

  return (
    <ModalForm<AdminSecurityAPI.SecRole>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.admin.security.role'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.admin.security.role'})
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
        code: data?.code,
        name: data?.name,
        type: data?.type?.value,
        status: data?.status?.value,
        remark: data?.remark
      }}
      onFinish={async (values: Record<string, any>) => {

        return data?.id
          ? RoleService.update({...values}).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : RoleService.add({...values}).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.new.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
      }}
    >
      <ProFormText name="id" hidden/>
      <ProFormText
        name={"name"}
        label={intl.formatMessage({id: 'pages.admin.security.role.name'})}
        rules={[{required: true}]}
      />
      <ProFormText
        name={"code"}
        label={intl.formatMessage({id: 'pages.admin.security.role.code'})}
        rules={[{required: true}]}
      />
      <ProFormSelect
        name={"type"}
        label={intl.formatMessage({id: 'pages.admin.security.role.type'})}
        allowClear={false}
        rules={[{required: true}]}
        initialValue={"02"}
        disabled={true}
        request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpSecRoleType)}
      />
      <ProFormSelect
        name={"status"}
        label={intl.formatMessage({id: 'pages.admin.security.role.status'})}
        request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpSecRoleStatus)}
        allowClear={false}
        rules={[{required: true}]}
      />
      <ProFormTextArea
        name={"remark"}
        label={intl.formatMessage({id: 'app.common.data.remark'})}
      />
    </ModalForm>
  );
};

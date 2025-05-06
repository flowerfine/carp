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
import {AdminSecurityAPI} from '@/services/admin/security/typings';
import {DictService} from '@/services/admin/system/dict.service';
import {DICT_TYPE} from '@/constants/dictType';
import {UserService} from '@/services/admin/security/user.service';

export default (props: ModalFormProps<AdminSecurityAPI.SecUser>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, onCancel, onFinish} = props;

  return (
    <ModalForm<AdminSecurityAPI.SecUser>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.admin.security.user'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.admin.security.user'})
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
        type: data?.type?.value,
        userName: data?.userName,
        nickName: data?.nickName,
        avatar: data?.avatar,
        email: data?.email,
        phone: data?.phone,
        status: data?.status?.value,
        order: data?.order,
        remark: data?.remark,
      }}
      onFinish={async (values: Record<string, any>) => {
        let user: AdminSecurityAPI.SecUser = {
          id: values.id,
          type: values.type,
          userName: values.userName,
          password: values.password,
          nickName: values.nickName,
          avatar: values.avatar,
          email: values.email,
          phone: values.phone,
          status: values.status,
          order: values.order,
          remark: values.remark,
        };
        return data?.id
          ? UserService.update(user).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : UserService.add(user).then((response) => {
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
      <ProFormSelect
        name="type"
        label={intl.formatMessage({id: 'pages.admin.security.user.type'})}
        rules={[{required: true}]}
        disabled={data?.id ? true : false}
        request={() => {
          return DictService.listInstanceByDefinition(DICT_TYPE.carpSecUserType)
        }}
      />
      <ProFormText
        name="userName"
        label={intl.formatMessage({id: 'pages.admin.security.user.userName'})}
        disabled={data?.id ? true : false}
        rules={[
          {required: true},
          {max: 30},
          {min: 5},
          {
            pattern: /^[a-zA-Z0-9_]+$/,
            message: intl.formatMessage({id: 'app.common.validate.characterWord'}),
          }
        ]}
      />
      <ProFormDependency name={["id"]}>
        {({id}) => {
          if (id) {
            return (<></>)
          }
          return <ProFormText.Password
            name="password"
            label={intl.formatMessage({id: 'pages.admin.security.user.password'})}
            rules={[{required: true}, {max: 50}]}
          />;
        }}
      </ProFormDependency>
      <ProFormText
        name="nickName"
        label={intl.formatMessage({id: 'pages.admin.security.user.nickName'})}
        rules={[{required: true}, {max: 50}]}
      />
      <ProFormText
        name="email"
        label={intl.formatMessage({id: 'pages.admin.security.user.email'})}
        rules={[
          {max: 100},
          {type: 'email'}
        ]}
      />
      <ProFormText
        name="phone"
        label={intl.formatMessage({id: 'pages.admin.security.user.phone'})}
        rules={[{max: 30}]}
      />
      <ProFormSelect
        name="status"
        label={intl.formatMessage({id: 'pages.admin.security.user.status'})}
        rules={[{required: true}]}
        allowClear={false}
        request={() => {
          return DictService.listInstanceByDefinition(DICT_TYPE.carpSecUserStatus)
        }}
      />
      <ProFormDigit
        name="order"
        label={intl.formatMessage({id: 'pages.admin.security.user.order'})}
        initialValue={0}
        min={0}
      />
      <ProFormTextArea
        name={"remark"}
        label={intl.formatMessage({id: 'app.common.data.remark'})}
      />
    </ModalForm>
  );
};

import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormSelect, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormParentProps} from "@/typings";
import {AdminSecurityAPI} from '@/services/admin/security/typings';
import {DictService} from '@/services/admin/system/dict.service';
import {DICT_TYPE} from '@/constants/dictType';
import {DeptService} from '@/services/admin/security/dept.service';

export default (props: ModalFormParentProps<AdminSecurityAPI.SecDept>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, parent, onCancel, onFinish} = props;

  return (
    <ModalForm<AdminSecurityAPI.SecDept>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.admin.security.dept'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.admin.security.dept'})
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
        pid: parent?.id ? parent.id : (data?.pid ? data.pid : 0),
        name: data?.name,
        code: data?.code,
        status: data?.status?.value,
        remark: data?.remark
      }}
      onFinish={async (values: Record<string, any>) => {
        return data?.id
          ? DeptService.update({...values}).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : DeptService.add({...values}).then((response) => {
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
          name={"pid"}
          label={intl.formatMessage({id: 'pages.admin.security.dept.pid'})}
          disabled
          request={((params, props) => {
            const webParam: AdminSecurityAPI.SecDeptParam = {
                name: params.keyWords
            };
            return DeptService.listAll(webParam).then((response) => {
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
          name={"name"}
          label={intl.formatMessage({id: 'pages.admin.security.dept.name'})}
        />
        <ProFormText
          name={"code"}
          label={intl.formatMessage({id: 'pages.admin.security.dept.code'})}
        />
        <ProFormSelect
          name={"status"}
          label={intl.formatMessage({id: 'pages.admin.security.dept.status'})}
          request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpSecDeptStatus)}
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

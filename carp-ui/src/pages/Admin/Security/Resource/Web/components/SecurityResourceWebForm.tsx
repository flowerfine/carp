import React from 'react';
import {Form, message} from 'antd';
import {ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from '@ant-design/pro-components';
import {useIntl} from '@umijs/max';
import {ModalFormParentProps} from "@/typings";
import {AdminSecurityAPI} from '@/services/admin/security/typings';
import {DictService} from '@/services/admin/system/dict.service';
import {DICT_TYPE} from '@/constants/dictType';
import {ResourceWebService} from '@/services/admin/security/resourceWeb.service';

export default (props: ModalFormParentProps<AdminSecurityAPI.SecResourceWeb>) => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const {visible, data, parent, onCancel, onFinish} = props;

  return (
    <ModalForm<AdminSecurityAPI.SecResourceWeb>
      title={
        data?.id
          ? intl.formatMessage({id: 'app.common.operate.edit.label'}) +
          intl.formatMessage({id: 'pages.admin.security.resource.web'})
          : intl.formatMessage({id: 'app.common.operate.new.label'}) +
          intl.formatMessage({id: 'pages.admin.security.resource.web'})
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
        type: data?.type?.value,
        value: data?.value,
        label: data?.label,
        path: data?.path,
        order: data?.order,
        status: data?.status?.value,
        remark: data?.remark
      }}
      onFinish={async (values: Record<string, any>) => {
        return data?.id
          ? ResourceWebService.update({...values}).then((response) => {
            if (response.success) {
              message.success(intl.formatMessage({id: 'app.common.operate.edit.success'}));
              if (onFinish) {
                onFinish(values);
              }
            }
          })
          : ResourceWebService.add({...values}).then((response) => {
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
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.pid'})}
          disabled
          request={((params, props) => {
            const webParam: AdminSecurityAPI.SecResourceWebParam = {
                label: params.keyWords
            };
            return ResourceWebService.listAll(webParam).then((response) => {
                if (response.data) {
                    return response.data?.map((item) => {
                        return {label: item.label, value: item.id, item: item};
                      });
                }
                return []
            });
          })}
        />
        <ProFormSelect
          name={"type"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.type'})}
          request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpSecResourceWebType)}
          allowClear={false}
          rules={[{required: true}]}
        />
        <ProFormText
          name={"value"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.value'})}
        />
        <ProFormText
          name={"label"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.label'})}
        />
        <ProFormText
          name={"path"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.path'})}
        />
        <ProFormDigit
          name={"order"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.order'})}
          min={0}
        />
        <ProFormSelect
          name={"status"}
          label={intl.formatMessage({id: 'pages.admin.security.resource.web.status'})}
          request={() => DictService.listInstanceByDefinition(DICT_TYPE.carpSecResourceStatus)}
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

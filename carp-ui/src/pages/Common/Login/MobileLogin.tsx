import React from "react";
import {FormattedMessage, useIntl} from "@umijs/max";
import {ProFormCaptcha, ProFormText} from '@ant-design/pro-components';
import {LockOutlined, MobileOutlined} from '@ant-design/icons';
import {getFakeCaptcha} from "@/services/ant-design-pro/login";

const MobileLoginWeb: React.FC = () => {
  const intl = useIntl();

  return (
    <>
      <ProFormText
        name="mobile"
        placeholder={intl.formatMessage({id: 'pages.common.login.mobileLogin.mobile.placeholder'})}
        fieldProps={{size: 'large', prefix: <MobileOutlined/>}}
        rules={[
          {
            required: true,
            message: (<FormattedMessage id="pages.common.login.mobileLogin.mobile.required"/>),
          },
          {
            pattern: /^1\d{10}$/,
            message: (<FormattedMessage id="pages.common.login.mobileLogin.mobile.invalid"/>
            ),
          },
        ]}
      />
      <ProFormCaptcha
        name="captcha"
        placeholder={intl.formatMessage({id: 'pages.common.login.mobileLogin.captcha.placeholder'})}
        fieldProps={{size: 'large', prefix: <LockOutlined/>}}
        rules={[
          {
            required: true,
            message: (<FormattedMessage id="pages.common.login.mobileLogin.captcha.required"/>),
          },
        ]}
        captchaProps={{
          size: 'large',
        }}
        captchaTextRender={(timing, count) => {
          if (timing) {
            return `${count} ${intl.formatMessage({id: 'pages.common.login.mobileLogin.captcha.get'})}`;
          }
          return intl.formatMessage({id: 'pages.common.login.mobileLogin.captcha.get'});
        }}
        onGetCaptcha={async (phone) => {
          const result = await getFakeCaptcha({
            phone,
          });
          if (!result) {
            return;
          }
        }}
      />
    </>
  );
};

export default MobileLoginWeb;

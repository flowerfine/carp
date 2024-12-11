import React, {useEffect} from "react";
import {FormattedMessage, useIntl, useModel} from "@umijs/max";
import {ProFormText} from '@ant-design/pro-components';
import {LockOutlined, SafetyCertificateOutlined, UserOutlined} from '@ant-design/icons';
import {Col, Form, Input, Row} from 'antd';
import {AuthenticationService} from "@/services/admin/security/authentication.service";

const AccountLoginWeb: React.FC = () => {
  const intl = useIntl();
  const {authCode, setAuthCode} = useModel('Common.Login.model');

  useEffect(() => {
    refreshAuthCode();
  }, []);

  const refreshAuthCode = async () => {
    AuthenticationService.getAuthImage().then((response) => {
      if (response.data) {
        setAuthCode(response.data)
      }
    })
  };

  return (
    <>
      <ProFormText
        name="userName"
        placeholder={intl.formatMessage({id: 'pages.common.login.accountLogin.userName.placeholder'})}
        fieldProps={{size: 'large', prefix: <UserOutlined/>}}
        rules={[
          {
            required: true,
            message: (<FormattedMessage id="pages.common.login.accountLogin.userName.required"/>),
          },
        ]}
      />
      <ProFormText.Password
        name="password"
        placeholder={intl.formatMessage({id: 'pages.common.login.accountLogin.password.placeholder'})}
        fieldProps={{size: 'large', prefix: <LockOutlined/>}}
        rules={[
          {
            required: true,
            message: (<FormattedMessage id="pages.common.login.accountLogin.password.required"/>)
          },
        ]}
      />
      <Form.Item
        name="authCode"
        rules={[
          {
            required: true,
            message: (<FormattedMessage id="pages.common.login.accountLogin.authCode.required"/>),
          }
        ]}
      >
        <Row gutter={[16, 0]}>
          <Col span={15}>
            <Form.Item noStyle>
              <Input
                placeholder={intl.formatMessage({id: "pages.common.login.accountLogin.authCode.placeholder"})}
                size="large"
                prefix={<SafetyCertificateOutlined/>}
              />
            </Form.Item>
          </Col>
          <Col span={9}>
            <img
              src={authCode?.img}
              alt={intl.formatMessage({id: "pages.common.login.accountLogin.authCode"})}
              onClick={refreshAuthCode}
            />
          </Col>
        </Row>
      </Form.Item>
    </>
  );
};

export default AccountLoginWeb;

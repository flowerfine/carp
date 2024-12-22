import React, {useState} from 'react';
import {flushSync} from 'react-dom';
import {Alert, Form, message} from 'antd';
import {createStyles} from 'antd-style';
import {LoginForm, ProFormCheckbox,} from '@ant-design/pro-components';
import {FormattedMessage, Helmet, SelectLang, useIntl, useModel} from '@umijs/max';
import {Footer} from '@/components';
import Settings from '../../../../config/defaultSettings';
import AccountLoginWeb from "@/pages/Common/Login/AccountLogin";
import {AdminSecurityAPI} from "@/services/admin/security/typings";
import {AuthenticationService} from "@/services/admin/security/authentication.service";

const useStyles = createStyles(({token}) => {
  return {
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage: "url('/images/bg.png')",
      backgroundSize: '100% 100%',
    },
  };
});

const Lang = () => {
  const {styles} = useStyles();
  return (
    <div className={styles.lang} data-lang>
      {SelectLang && <SelectLang/>}
    </div>
  );
};

const LoginMessage: React.FC<{
  content: string;
}> = ({content}) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};

const Login: React.FC = () => {
  const intl = useIntl();
  const {initialState, setInitialState} = useModel('@@initialState');
  const [type, setType] = useState<string>('account');
  const {styles} = useStyles();
  const [form] = Form.useForm()
  const {authCode, setAuthCode} = useModel('Common.Login.model');

  const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
  const {status, type: loginType} = userLoginState;

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      flushSync(() => {
        setInitialState((s) => ({...s, currentUser: userInfo}));
      });
    }
  };

  const handleSubmit = async (values: AdminSecurityAPI.LoginInfo) => {
    try {
      AuthenticationService.login({...values, uuid: authCode.uuid}).then(async (response) => {
        if (response.success) {
          const defaultLoginSuccessMessage = intl.formatMessage({
            id: 'pages.login.success',
            defaultMessage: '登录成功！',
          });
          message.success(defaultLoginSuccessMessage);
          await fetchUserInfo();
          const urlParams = new URL(window.location.href).searchParams;
          window.location.href = urlParams.get('redirect') || '/';
          return;
        } else {
          form.resetFields()
        }
      })
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      message.error(defaultLoginFailureMessage);
    }
  };

  return (
    <div className={styles.container}>
      <Helmet>
        <title>
          {intl.formatMessage({id: 'menu.login'})}- {Settings.title}
        </title>
      </Helmet>
      <Lang/>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src="/logo.svg"/>}
          title={Settings.title}
          subTitle={intl.formatMessage({id: 'pages.common.login.subTitle'})}
          form={form}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as AdminSecurityAPI.LoginInfo);
          }}
        >

          {status === 'error' && (
            <LoginMessage content={intl.formatMessage({id: 'pages.common.login.accountLogin.failure'})}/>
          )}
          {type === 'account' && (<AccountLoginWeb/>)}

          <div style={{marginBottom: 24}}>
            <ProFormCheckbox name="autoLogin" noStyle>
              <FormattedMessage id="pages.common.login.autoLogin"/>
            </ProFormCheckbox>
            <a style={{float: 'right'}}>
              <FormattedMessage id="pages.common.login.forgotPassword"/>
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  );
};

export default Login;

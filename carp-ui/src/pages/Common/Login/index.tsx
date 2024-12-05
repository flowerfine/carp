import React, {useState} from "react";
import {Form} from "antd";
import {FormattedMessage, useIntl, useModel, useNavigate} from "@umijs/max";
import { createStyles } from 'antd-style';
import styles from "../index.less";
import {AdminSecurityAPI} from "@/services/admin/security/typings";

const LoginWeb: React.FC = () => {
  const intl = useIntl();
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [authCode, setAuthCode] = useState<AdminSecurityAPI.AuthCode>();
  const {initialState, setInitialState} = useModel("@@initialState");

  return (
    <div className={styles.container}>
      登陆页面
    </div>
  );
};

export default LoginWeb;

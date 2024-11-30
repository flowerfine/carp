import React, {useEffect, useState} from "react";
import {flushSync} from "react-dom";
import {Button, Checkbox, Col, Form, Input, message, Row} from "antd";
import {Helmet, useIntl, useModel, useNavigate} from "@umijs/max";
import {USER_AUTH} from "@/constants/constant";
import styles from "../index.less";
import {AuthenticationService} from "@/services/admin/security/authentication.service";
import {AdminSecurityAPI} from "@/services/admin/security/typings";

const Login: React.FC = () => {
    const intl = useIntl();
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const [authCode, setAuthCode] = useState<AdminSecurityAPI.AuthCode>();
    const {initialState, setInitialState} = useModel("@@initialState");

    useEffect(() => {
        localStorage.clear();
        refreshAuthCode();
    }, []);

    const refreshAuthCode = async () => {
        AuthenticationService.getAuthImage().then((response) => setAuthCode(response.data))
    };

    const handleSubmit = async () => {
        try {
            form.validateFields().then((values: AdminSecurityAPI.LoginInfo) => {
                const params: AdminSecurityAPI.LoginInfo = {...values, uuid: authCode?.uuid as string,};
                AuthenticationService.login(params).then(async (resp) => {
                    if (resp.success && resp.data) {
                        const onlineUserInfo = resp.data
                        localStorage.setItem(USER_AUTH.token, onlineUserInfo.token);
                        message.success(intl.formatMessage({id: "pages.user.login.success"}));
                        await flushSync(() => {
                            setInitialState((state) => ({
                                ...state,
                                currentUser: onlineUserInfo,
                            }));
                        });
                        AuthenticationService.storeSession(onlineUserInfo)
                        setTimeout(() => {
                            navigate("/");
                        }, 500);
                    } else {
                        refreshAuthCode();
                    }
                });
            });
        } catch (error) {
            message.error(intl.formatMessage({id: "pages.user.login.failure"}));
        }
    };

    return (
        <div className={styles.container}>
            <Helmet>
                <title>
                    {intl.formatMessage({id: "menu.login"})}-{" Scaleph"}
                </title>
            </Helmet>
            <div className={styles.mainContent}>
                <div className={styles.logoInfo}>

                    <span className={styles.title}>{initialState?.settings?.title}</span>
                </div>
                <div className={styles.loginForm}>
                    <Form
                        form={form}
                        layout="vertical"
                        initialValues={{autoLogin: true}}
                    >
                        <Form.Item
                            name="userName"
                            label={intl.formatMessage({id: "pages.user.login.userName"})}
                            rules={[
                                {required: true},
                                {max: 30},
                                {
                                    message: intl.formatMessage({id: "pages.common.validate.characterWord"}),
                                },
                            ]}
                        >
                            <Input
                                placeholder={intl.formatMessage({id: "pages.user.login.userName.placeholder"})}
                            />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            label={intl.formatMessage({id: "pages.user.login.password"})}
                            rules={[{required: true, min: 6, max: 32}]}
                        >
                            <Input.Password
                                placeholder={intl.formatMessage({id: "pages.user.login.password.placeholder"})}
                            />
                        </Form.Item>
                        <Form.Item
                            name="authCode"
                            label={intl.formatMessage({id: "pages.user.login.authCode"})}
                            rules={[{required: true, len: 5}]}
                        >
                            <Row gutter={[16, 0]}>
                                <Col span={15}>
                                    <Form.Item noStyle>
                                        <Input
                                            placeholder={intl.formatMessage({id: "pages.user.login.authCode.placeholder"})}
                                        />
                                    </Form.Item>
                                </Col>
                                <Col span={9}>
                                    <img
                                        src={authCode?.img}
                                        alt={intl.formatMessage({id: "pages.user.login.authCode"})}
                                        onClick={refreshAuthCode}
                                    />
                                </Col>
                            </Row>
                        </Form.Item>
                        <Form.Item style={{marginBottom: "12px"}}>
                            <Form.Item noStyle name="autoLogin" valuePropName="checked">
                                <Checkbox>
                                    {intl.formatMessage({id: "pages.user.login.autoLogin"})}
                                </Checkbox>
                            </Form.Item>
                            <div style={{float: "right"}}>
                                <a>
                                    {intl.formatMessage({id: "pages.user.login.forget"})}
                                </a>
                            </div>
                        </Form.Item>
                        <Form.Item style={{marginBottom: "6px"}}>
                            <Button
                                type="primary"
                                size="large"
                                htmlType="submit"
                                style={{width: "100%"}}
                                onClick={handleSubmit}
                            >
                                {intl.formatMessage({id: "pages.user.login.login"})}
                            </Button>
                        </Form.Item>
                        <Form.Item>
                            <a href="/user/register" style={{float: "right"}}>
                                {intl.formatMessage({id: "pages.user.login.register"})}
                            </a>
                        </Form.Item>
                    </Form>
                </div>
            </div>
        </div>
    );
};

export default Login;

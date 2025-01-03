import {AvatarDropdown, AvatarName, Footer, Question, SelectLang} from '@/components';
import {LinkOutlined} from '@ant-design/icons';
import type {Settings as LayoutSettings} from '@ant-design/pro-components';
import {SettingDrawer} from '@ant-design/pro-components';
import type {RequestConfig, RunTimeLayoutConfig} from '@umijs/max';
import {history, Link} from '@umijs/max';
import queryString from 'query-string';
import defaultSettings from '../config/defaultSettings';
import {errorConfig} from './requestErrorConfig';
import {AdminSecurityAPI} from "@/services/admin/security/typings";
import {AuthenticationService} from "@/services/admin/security/authentication.service";

const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';

const login = () => {
  const query = queryString.parse(history.location.search);
  const { redirect_uri } = query as { redirect_uri: string };
  history.replace({
    pathname: AuthenticationService.loginPath,
    search:
      redirect_uri &&
      queryString.stringify({
        redirect_uri: redirect_uri,
      }),
  });
};

const fetchUserInfo = async () => {
  try {
    const response = await AuthenticationService.getOnlineUserInfo();
    if (response.success && response.data) {
      return response.data;
    } else {
      login()
    }
  } catch (error) {
    login()
  }
  return undefined;
};

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: AdminSecurityAPI.OnlineUserInfo;
  loading?: boolean;
  fetchUserInfo?: () => Promise<AdminSecurityAPI.OnlineUserInfo | undefined>;
}> {
  return {
    fetchUserInfo,
    // 如果不是登录页面，执行
    currentUser: AuthenticationService.isLoginPath() || AuthenticationService.isRegisterPath() || AuthenticationService.isRegisterResultPath() ? undefined : await fetchUserInfo(),
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({initialState, setInitialState}) => {
  return {
    actionsRender: () => [<Question key="doc"/>, <SelectLang key="SelectLang"/>],
    avatarProps: {
      src: initialState?.currentUser?.avatar,
      title: <AvatarName/>,
      render: (_, avatarChildren) => {
        return <AvatarDropdown>{avatarChildren}</AvatarDropdown>;
      },
    },
    waterMarkProps: {
      content: initialState?.currentUser?.userName,
    },
    footerRender: () => <Footer/>,
    onPageChange: () => {
      const {location} = history;
      // 如果没有登录，重定向到 login
      if ((!initialState || !initialState?.currentUser) && location.pathname !== AuthenticationService.loginPath) {
        history.push(AuthenticationService.loginPath);
      }
    },
    bgLayoutImgList: [
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr',
        left: 85,
        bottom: 100,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr',
        bottom: -68,
        right: -45,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
    links: isDev
      ? [
        <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
          <LinkOutlined/>
          <span>OpenAPI 文档</span>
        </Link>,
      ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {isDev && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request: RequestConfig = {
  ...errorConfig,
  timeout: 1800000
};

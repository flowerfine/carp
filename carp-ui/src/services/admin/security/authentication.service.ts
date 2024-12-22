import {ResponseBody} from '@/typings';
import {request, matchPath, history} from '@umijs/max';
import {AdminSecurityAPI} from "@/services/admin/security/typings";
import {USER_AUTH} from "@/constants/constant";

export const AuthenticationService = {
    url: '/api/carp/security/authentication',
    loginPath: '/user/login',
    registerPath: '/user/register',
    registerResult: '/user/register-result',

    getAuthImage: async () => {
        return request<ResponseBody<AdminSecurityAPI.AuthCode>>(`${AuthenticationService.url}/captcha`, {
            method: 'GET'
        });
    },

    isLoginPath: () => {
        return matchPath(
            {
              path: AuthenticationService.loginPath,
            },
            history.location.pathname,
          );
    },

    isRegisterPath: () => {
        return matchPath(
            {
              path: AuthenticationService.registerPath,
            },
            history.location.pathname,
          );
    },

    isRegisterResultPath: () => {
        return matchPath(
            {
              path: AuthenticationService.registerResult,
            },
            history.location.pathname,
          );
    },

    login: async (loginInfo: AdminSecurityAPI.LoginInfo) => {
        return request<ResponseBody<AdminSecurityAPI.OnlineUserInfo>>(`${AuthenticationService.url}/login`, {
            method: 'POST',
            data: loginInfo,
        });
    },

    storeSession: async (userInfo: AdminSecurityAPI.OnlineUserInfo) => {
        localStorage.setItem(USER_AUTH.userInfo, JSON.stringify(userInfo));
        let pCodes: string[] = [];
        if (userInfo.roles) {
            userInfo.roles.forEach((role) => {
                pCodes.push(role.code);
            });
        }

        if (userInfo.resourceWebs) {
            userInfo.resourceWebs.forEach((resourceWeb) => {
                pCodes.push(resourceWeb.name);
            });
        }

        localStorage.setItem(USER_AUTH.pCodes, JSON.stringify(pCodes));
    },

    getOnlineUserInfo: async () => {
        return request<ResponseBody<AdminSecurityAPI.OnlineUserInfo>>(`${AuthenticationService.url}/onlineUser`, {
            method: 'GET',
        });
    },

    logout: async () => {
        let token: string = localStorage.getItem(USER_AUTH.token) || '';
        request<ResponseBody<any>>('/api/user/logout', {
            method: 'POST',
            params: {token: token},
        });
    },
};

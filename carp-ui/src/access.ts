import { AdminSecurityAPI } from "./services/admin/security/typings";

/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: AdminSecurityAPI.OnlineUserInfo } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    canAdmin: currentUser && currentUser.roles?.find(item => item.code === 'sys_user_admin' || item.code === 'sys_admin' ) != undefined,
    canAccess: (code: string) => {
      return true
    },
    normalRouteFilter: (route: any) => {
      return true;
    },
  };
}

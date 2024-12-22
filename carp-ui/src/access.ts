import { AdminSecurityAPI } from "./services/admin/security/typings";

/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: AdminSecurityAPI.OnlineUserInfo } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    canAdmin: currentUser && currentUser.roles?.find(item => item.code === 'sys_user_admin' || item.code === 'sys_admin' ) != undefined,
    canAccess: (code: string) => {
      if (!currentUser) {
        return false
      }
      console.log('canAccess', route, currentUser)
      return true
    },
    normalRouteFilter: (route: any) => {
      if (!currentUser) {
        return false
      }
      console.log('normalRouteFilter', route, currentUser)
      return true;
    },
  };
}

﻿/**
 * @name umi 的路由配置
 * @description 只支持 path,component,routes,redirect,wrappers,name,icon 的配置
 * @param path  path 只支持两种占位符配置，第一种是动态参数 :id 的形式，第二种是 * 通配符，通配符只能出现路由字符串的最后。
 * @param component 配置 location 和 path 匹配后用于渲染的 React 组件路径。可以是绝对路径，也可以是相对路径，如果是相对路径，会从 src/pages 开始找起。
 * @param routes 配置子路由，通常在需要为多个路径增加 layout 组件时使用。
 * @param redirect 配置路由跳转
 * @param wrappers 配置路由组件的包装组件，通过包装组件可以为当前的路由组件组合进更多的功能。 比如，可以用于路由级别的权限校验
 * @param name 配置路由的标题，默认读取国际化文件 menu.ts 中 menu.xxxx 的值，如配置 name 为 login，则读取 menu.ts 中 menu.login 的取值作为标题
 * @param icon 配置路由的图标，取值参考 https://ant.design/components/icon-cn， 注意去除风格后缀和大小写，如想要配置图标为 <StepBackwardOutlined /> 则取值应为 stepBackward 或 StepBackward，如想要配置图标为 <UserOutlined /> 则取值应为 user 或者 User
 * @doc https://umijs.org/docs/guides/routes
 */
export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        redirect: '/user/login',
      },
      {
        path: '/user/login',
        layout: false,
        name: 'login',
        component: './Common/Login',
        // component: './user/login',
      },
      {
        name: 'register-result',
        icon: 'smile',
        path: '/user/register-result',
        component: './user/register-result',
      },
      {
        name: 'register',
        icon: 'smile',
        path: '/user/register',
        component: './user/register',
      }
    ],
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    icon: 'dashboard',
    routes: [
      {
        path: '/dashboard',
        redirect: '/dashboard/analysis',
      },
      {
        name: 'analysis',
        icon: 'smile',
        path: '/dashboard/analysis',
        component: './dashboard/analysis',
      },
      {
        name: 'monitor',
        icon: 'smile',
        path: '/dashboard/monitor',
        component: './dashboard/monitor',
      },
      {
        name: 'workplace',
        icon: 'smile',
        path: '/dashboard/workplace',
        component: './dashboard/workplace',
      },
    ],
  },
  {
    path: '/form',
    icon: 'form',
    name: 'form',
    routes: [
      {
        path: '/form',
        redirect: '/form/basic-form',
      },
      {
        name: 'basic-form',
        icon: 'smile',
        path: '/form/basic-form',
        component: './form/basic-form',
      },
      {
        name: 'step-form',
        icon: 'smile',
        path: '/form/step-form',
        component: './form/step-form',
      },
      {
        name: 'advanced-form',
        icon: 'smile',
        path: '/form/advanced-form',
        component: './form/advanced-form',
      },
    ],
  },
  {
    path: '/list',
    icon: 'table',
    name: 'list',
    routes: [
      {
        path: '/list/search',
        name: 'search-list',
        component: './list/search',
        routes: [
          {
            path: '/list/search',
            redirect: '/list/search/articles',
          },
          {
            name: 'articles',
            icon: 'smile',
            path: '/list/search/articles',
            component: './list/search/articles',
          },
          {
            name: 'projects',
            icon: 'smile',
            path: '/list/search/projects',
            component: './list/search/projects',
          },
          {
            name: 'applications',
            icon: 'smile',
            path: '/list/search/applications',
            component: './list/search/applications',
          },
        ],
      },
      {
        path: '/list',
        redirect: '/list/table-list',
      },
      {
        name: 'table-list',
        icon: 'smile',
        path: '/list/table-list',
        component: './table-list',
      },
      {
        name: 'basic-list',
        icon: 'smile',
        path: '/list/basic-list',
        component: './list/basic-list',
      },
      {
        name: 'card-list',
        icon: 'smile',
        path: '/list/card-list',
        component: './list/card-list',
      },
    ],
  },
  {
    path: '/metadata',
    name: 'metadata',
    icon: 'gateway',
    routes: [
      {
        path: '/metadata',
        redirect: '/metadata/gravitino/metalake'
      },
      {
        name: 'gravitino',
        path: '/metadata/gravitino',
        routes: [
          {
            name: 'metalake',
            path: '/metadata/gravitino/metalake',
            component: './Metadata/Gravitino/Metalake',
          },
          {
            path: '/metadata/gravitino/metalake/catalog',
            component: './Metadata/Gravitino/Metalake/Catalog',
          }
        ]
      }
    ]
  },
  {
    path: '/workspace',
    name: 'workspace',
    icon: 'profile',
    routes: [
      {
        path: '/workspace',
        redirect: '/workspace/schedule',
      },
      {
        path: '/workspace/schedule',
        name: 'schedule',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/schedule',
            redirect: '/workspace/schedule/group',
          },
          {
            path: '/workspace/schedule/group',
            name: 'group',
            icon: 'profile',
            component: './Workspace/Schedule/Group'
          },
          {
            path: '/workspace/schedule/config',
            name: 'config',
            icon: 'profile',
            component: './Workspace/Schedule/Config',
          },
          {
            path: '/workspace/schedule/instance',
            component: './Workspace/Schedule/Instance'
          },
        ]
      },
      {
        path: '/workspace/orca',
        name: 'orca',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/orca',
            redirect: '/workspace/orca/instance',
          },
          {
            path: '/workspace/orca/instance',
            name: 'instance',
            icon: 'profile',
            component: './Workspace/Orca/Instance',
          },
          {
            path: '/workspace/orca/detail',
            component: './Workspace/Orca/Detail',
          },
        ]
      },
      {
        path: '/workspace/x6',
        name: 'x6',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/x6',
            redirect: '/workspace/x6/dag-demo',
          },
          {
            path: '/workspace/x6/dag-demo',
            name: 'dag-demo',
            icon: 'profile',
            component: './Workspace/X6/dag-demo',
          },
          {
            path: '/workspace/x6/dag-demo/detail',
            component: './Workspace/X6/dag-demo/dag'
          },
          {
            path: '/workspace/x6/dag',
            name: 'dag',
            icon: 'profile',
            component: './Workspace/X6/dag',
          },
          {
            path: '/workspace/x6/basic',
            name: 'basic',
            icon: 'profile',
            component: './Workspace/X6/basic',
          },
          {
            path: '/workspace/x6/dnd',
            name: 'dnd',
            icon: 'profile',
            component: './Workspace/X6/dnd',
          },
          {
            path: '/workspace/x6/diff',
            name: 'diff',
            icon: 'profile',
            component: './Workspace/X6/diff',
          },
          {
            path: '/workspace/x6/flow',
            name: 'flow',
            icon: 'profile',
            component: './Workspace/X6/flow',
          },
          {
            path: '/workspace/x6/group',
            name: 'group',
            icon: 'profile',
            component: './Workspace/X6/group',
          },
          {
            path: '/workspace/x6/drawing',
            name: 'drawing',
            icon: 'profile',
            component: './Workspace/X6/drawing',
          }
        ]
      },
    ]
  },
  {
    path: '/profile',
    name: 'profile',
    icon: 'profile',
    routes: [
      {
        path: '/profile',
        redirect: '/profile/basic',
      },
      {
        name: 'basic',
        icon: 'smile',
        path: '/profile/basic',
        component: './profile/basic',
      },
      {
        name: 'advanced',
        icon: 'smile',
        path: '/profile/advanced',
        component: './profile/advanced',
      },
    ],
  },
  {
    name: 'result',
    icon: 'CheckCircleOutlined',
    path: '/result',
    routes: [
      {
        path: '/result',
        redirect: '/result/success',
      },
      {
        name: 'success',
        icon: 'smile',
        path: '/result/success',
        component: './result/success',
      },
      {
        name: 'fail',
        icon: 'smile',
        path: '/result/fail',
        component: './result/fail',
      },
    ],
  },
  {
    name: 'exception',
    icon: 'warning',
    path: '/exception',
    routes: [
      {
        path: '/exception',
        redirect: '/exception/403',
      },
      {
        name: '403',
        icon: 'smile',
        path: '/exception/403',
        component: './exception/403',
      },
      {
        name: '404',
        icon: 'smile',
        path: '/exception/404',
        component: './exception/404',
      },
      {
        name: '500',
        icon: 'smile',
        path: '/exception/500',
        component: './exception/500',
      },
    ],
  },
  {
    name: 'account',
    icon: 'user',
    path: '/account',
    routes: [
      {
        path: '/account',
        redirect: '/account/center',
      },
      {
        name: 'center',
        icon: 'smile',
        path: '/account/center',
        component: './account/center',
      },
      {
        name: 'settings',
        icon: 'smile',
        path: '/account/settings',
        component: './account/settings',
      },
    ],
  },
  {
    name: 'admin',
    path: '/admin',
    icon: 'setting',
    routes: [
      {
        path: '/admin',
        redirect: '/admin/security',
      },
      {
        name: 'security',
        path: '/admin/security',
        icon: 'apartment',
        routes: [
          {
            path: '/admin/security',
            redirect: '/admin/security/dept',
          },
          {
            name: 'dept',
            path: '/admin/security/dept',
            icon: 'apartment',
            component: './Admin/Security/Dept',
          },
          {
            name: 'user',
            path: '/admin/security/user',
            icon: 'user',
            component: './Admin/Security/User',
          },
          {
            name: 'role',
            path: '/admin/security/role',
            icon: 'safety',
            component: './Admin/Security/Role',
          },
          {
            name: 'resource',
            path: '/admin/security/resource',
            icon: 'team',
            routes: [
              {
                name: 'web',
                path: '/admin/security/resource/web',
                icon: 'team',
                component: './Admin/Security/Resource/Web',
              }
            ]
          },
        ]
      }
    ],
  },
  {
    path: '/',
    redirect: '/dashboard/analysis',
  },
  {
    component: '404',
    path: '/*',
  },

];

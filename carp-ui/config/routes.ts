/**
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
    path: '/',
    redirect: '/metadata',
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
        path: '/workspace/http-sync',
        name: 'http-sync',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/http-sync',
            redirect: '/workspace/http-sync/groups',
          },
          {
            path: '/workspace/http-sync/groups',
            name: 'group',
            icon: 'profile',
            component: './Workspace/HttpSync/Group'
          }
        ]
      },
      {
        path: '/workspace/workflow',
        name: 'workflow',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/workflow',
            redirect: '/workspace/workflow/definition',
          },
          {
            path: '/workspace/workflow/definition',
            name: 'definition',
            icon: 'profile',
            component: './Workspace/Workflow/Definition'
          },
          {
            path: '/workspace/workflow/instance',
            component: './Workspace/Workflow/Instance'
          },
        ]
      },
      {
        path: '/workspace/serverless-workflow',
        name: 'serverless-workflow',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/serverless-workflow',
            redirect: '/workspace/serverless-workflow/config',
          },
          {
            path: '/workspace/serverless-workflow/config',
            name: 'config',
            icon: 'profile',
            component: './Workspace/ServerlessWorkflow/Config',
          },
          {
            path: '/workspace/serverless-workflow/config/dag',
            component: './Workspace/ServerlessWorkflow/Config/Dag',
          },
          {
            path: '/workspace/serverless-workflow/instance',
            name: 'instance',
            icon: 'profile',
            component: './Workspace/ServerlessWorkflow/Instance',
          },
          {
            path: '/workspace/serverless-workflow/instance/detail',
            component: './Workspace/ServerlessWorkflow/Instance/Detail',
          }
        ]
      },
      {
        path: '/workspace/flowgram',
        name: 'flowgram',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/flowgram',
            redirect: '/workspace/flowgram/demo-free-layout',
          },
          {
            path: '/workspace/flowgram/demo-free-layout',
            name: 'demo-free-layout',
            icon: 'profile',
            component: './Workspace/Flowgram/demo-free-layout',
          },
          {
            path: '/workspace/flowgram/demo-free-layout2',
            name: 'demo-free-layout2',
            icon: 'profile',
            component: './Workspace/Flowgram/demo-free-layout2',
          },
          {
            path: '/workspace/flowgram/demo-my-free-layout',
            name: 'demo-my-free-layout',
            icon: 'profile',
            component: './Workspace/Flowgram/demo-my-free-layout',
          },
          {
            path: '/workspace/flowgram/condition',
            name: 'condition',
            icon: 'profile',
            component: './Workspace/Flowgram/condition',
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
            redirect: '/workspace/x6/cep',
          },
          {
            path: '/workspace/x6/cep',
            name: 'cep',
            icon: 'profile',
            component: './Workspace/X6/cep',
          },
          {
            path: '/workspace/x6/chat',
            name: 'chat',
            icon: 'profile',
            component: './Workspace/X6/chat',
          },
          {
            path: '/workspace/x6/cicd',
            name: 'cicd',
            icon: 'profile',
            component: './Workspace/X6/cicd',
          },
          {
            path: '/workspace/x6/pipeline',
            name: 'pipeline',
            icon: 'profile',
            component: './Workspace/X6/pipeline',
          },
          {
            path: '/workspace/x6/dag',
            name: 'dag',
            icon: 'profile',
            component: './Workspace/X6/dag',
          },
          {
            path: '/workspace/x6/dataProcessingDag',
            name: 'dataProcessingDag',
            icon: 'profile',
            component: './Workspace/X6/dataProcessingDag',
          },
          {
            path: '/workspace/x6/mind',
            name: 'mind',
            icon: 'profile',
            component: './Workspace/X6/mind',
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
      {
        path: '/workspace/monitor',
        name: 'monitor',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/monitor',
            redirect: '/workspace/monitor/alert',
          },
          {
            path: '/workspace/monitor/alert',
            name: 'alert',
            icon: 'profile',
            routes: [
              {
                path: '/workspace/monitor/alert',
                redirect: '/workspace/monitor/alert/message',
              },
              {
                path: '/workspace/monitor/alert/message',
                name: 'message',
                icon: 'profile',
                component: './Workspace/Monitor/Alert/Message'
              },
              {
                path: '/workspace/monitor/alert/rule',
                name: 'rule',
                icon: 'profile',
                component: './Workspace/Monitor/Alert/Rule'
              }
            ]
          }
        ]
      },
      {
        path: '/workspace/cep',
        name: 'cep',
        icon: 'profile',
        routes: [
          {
            path: '/workspace/cep',
            redirect: '/workspace/cep/rule',
          },
          {
            path: '/workspace/cep/rule',
            name: 'rule',
            icon: 'profile',
            component: './Workspace/Cep/Rule'
          },
          {
            path: '/workspace/cep/rule/detail',
            component: './Workspace/Cep/Rule/Detail'
          }
        ]
      }
    ]
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
    component: '404',
    path: '/*',
  },
];

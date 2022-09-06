import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/client',
    children: [
      {
        path: '/client',
        name: 'client',
        component: () => import('@/views/client/index'),
        meta: { title: '客户端配置', icon: 'example' }
      }
    ]
  },

  {
    path: '/release',
    component: Layout,
    children: [
      {
        path: '/release',
        name: 'release',
        component: () => import('@/views/release/index'),
        meta: { title: '历史快照', icon: 'schedule' }
      }
    ]
  },

  {
    path: '/search',
    component: Layout,
    redirect: '/search',
    children: [
      {
        path: '/search',
        name: 'SearchResult',
        component: () => import('@/views/search/search-result'),
        meta: { title: '搜索结果', icon: 'example' },
        hidden: true
      }
    ]
  },

  {
    path: '/config',
    component: Layout,
    children: [
      {
        path: '/config',
        name: 'config',
        component: () => import('@/views/config/index'),
        meta: { title: '配置管理', icon: 'example' },
        hidden: true
      }
    ]
  },

  {
    path: '/group',
    component: Layout,
    children: [
      {
        path: '/group',
        name: 'group',
        component: () => import('@/views/groups/index'),
        meta: { title: '分组管理', icon: 'tools' }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [

  {
    path: '/cluster',
    component: Layout,
    children: [
      {
        path: '/cluster',
        component: () => import('@/views/cluster/index'),
        meta: { title: '集群管理', icon: 'tree', roles: ['SA', 'CA'] }
      }
    ]
  },

  {
    path: '/users',
    component: Layout,
    children: [
      {
        path: '/users',
        component: () => import('@/views/users/index'),
        meta: { title: '账号管理', icon: 'user', roles: ['SA'] }
      },
      {
        path: '/center',
        component: () => import('@/views/users/center'),
        meta: { title: '个人中心', icon: 'form', roles: ['SA', 'CA'] },
        hidden: true
      }
    ]
  },

  {
    path: '/system',
    component: Layout,
    meta: { title: '日志管理', icon: 'migrate', roles: ['SA'] },
    children: [
      // {
      //   path: 'settings',
      //   component: () => import('@/views/system/settings'),
      //   meta: { title: '系统设置', icon: 'settings', roles: ['SA'] }
      // },
      {
        path: 'audit',
        component: () => import('@/views/system/audit'),
        meta: { title: '审计日志', icon: 'logs', roles: ['SA'] },
      },
      {
        path: 'oplog',
        component: () => import('@/views/system/oplog'),
        meta: { title: '操作日志', icon: 'logs', roles: ['SA'] },
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

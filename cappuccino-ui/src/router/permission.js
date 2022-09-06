// 引入组件样式
import { MessageBox } from 'element-ui'
import NProgress from 'nprogress' 
import 'nprogress/nprogress.css' 
// router和store
import router from '@/router'
import store from '@/store'
// 令牌
import { getAccessToken } from '@/server/token' 
import getPageTitle from '@/utils/get-page-title'
import { redirectLogin } from '@/utils/error'

// NProgress配置
NProgress.configure({ showSpinner: false }) 

// 访问白名单
const whiteList = ['/login'] 

router.beforeEach(async(to, from, next) => {
  
    // 开启进度条和获取页面标题
    NProgress.start()
    document.title = getPageTitle(to.meta.title)
    next()

    // 获取访问令牌
    const hasToken = getAccessToken()

    if (hasToken) {
        if (to.path === '/login') 
        {
            // 当前已登录且处于登录页，则直接跳转至主页
            next({ path: '/' })
            NProgress.done()
        } 
        else 
        {
          next()
            // 当前已登录，判断是否有登录用户信息
            const hasAuthority = store.getters.roles && store.getters.roles>0
            if (hasAuthority) 
            {
                next()
            } 
            else 
            {
                try {
                    const roles = await store.dispatch('user/getInfo')
                    const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
                    router.addRoutes(accessRoutes)
                    next({ ...to, replace: true })


                    // store.dispatch('user/getInfo').then(res => { // 拉取info
                    //   let roles = res.data.roles;
                    //   store.dispatch('permission/generateRoutes', {roles}).then(() => { // 生成可访问的路由表
                    //     router.addRoutes(store.getters.permission_routes) // 动态添加可访问路由表
                    //     next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                    //   })
                    // }).catch(err => {
                    //   console.log(err)
                    // })

                } catch (error) {
                    // 未分配任何菜单或权限
                    console.log(error)
                    redirectLogin('您的账号授权异常', next, to)  
                    NProgress.done()
                }
            }
        }
    } 
    else 
    {
        if (whiteList.indexOf(to.path) !== -1) {
            // 白名单放行
            next()
        } else {
            // 重定向至登录页
            redirectLogin('您的身份认证已过期，请重新登录', next, to)    
            NProgress.done()  
        }
    }
})

/**
 * 重定向到登录页
 * @param {*} message 
 * @param {*} next 
 * @param {*} to 
 */
// function redirectLogin(message, next, to) {
//     let count = get()
//     if (!count || count===0) {
//         set(1)
//         MessageBox.alert(message, '提示', {
//             confirmButtonText: '确定',
//             type: 'error',
//             center: true
//         }).then(() => {
//             store.dispatch('user/resetToken').then(() => {
//                 next(`/login?redirect=${to.path}`)
//             })
//             remove()
//             NProgress.done()
//         })
//     }
// }

/**
 * 路由钩子结束
 */
router.afterEach(() => {
  NProgress.done()
})

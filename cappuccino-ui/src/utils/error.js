// router和store
import router from '@/router'
import store from '@/store'
import { debounce } from '@/utils/debounce'
import { MessageBox } from 'element-ui'

export let redirectLogin = debounce((message, next, to) => {
    MessageBox.alert(message, '提示', {
        confirmButtonText: '确定',
        type: 'error',
        center: true
    }).then(() => {
        store.dispatch('user/resetToken').then(() => {
            if (next && to) {
                next(`/login?redirect=${to.path}`)
            } else {
                router.push({path: '/login'})
            }
        })
    })
}, 1500)
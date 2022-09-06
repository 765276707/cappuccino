import request from '@/server/request'

const FUNC_PATH = "/auth"

export default {
    login,
    logout,
    getInfo,
    getLoginSelf
}

/**
 * 登录
 * @param data 
 */
function login(data) {
    return request.post(FUNC_PATH+'/login', data)
}

function logout() {
    return request.delete(FUNC_PATH+'/logout')
}

function getInfo() {
    return request.get(FUNC_PATH+'/getInfo')
}

function getLoginSelf() {
    return request.get(FUNC_PATH+'/getLoginSelf')
}
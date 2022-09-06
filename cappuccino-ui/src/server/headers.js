import { getAccessToken } from '@/server/token'

/**
 * @title 获取请求所需的请求头参数
 * @description 用于上传、下载等绕开Axios异步请求的使用场景
 */
 export function getHeaders() {
    let headers = {
        // 访问令牌
        'Authorization': getAccessToken()
    }
    return headers
}
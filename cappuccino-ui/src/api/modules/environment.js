import request from '@/server/request'

const FUNC_PATH = "/environment"

export default {
    getList
}

/**
 * 查询列表 
 */
function getList(excludeId) {
    return request.get(FUNC_PATH+'/getList', {params: {'excludeId': excludeId}})
}

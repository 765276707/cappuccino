import request from '@/server/request'

const FUNC_PATH = "/clusters"

export default {
    getInfo
}

function getInfo() {
    return request.get(FUNC_PATH + '/getInfo')
}

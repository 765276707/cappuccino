import request from '@/server/request'

const FUNC_PATH = "/oplog"

export default {
    getPage
}

function getPage(searchForm) {
    return request.get(FUNC_PATH+'/getPage', {params: searchForm})
}


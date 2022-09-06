import request from '@/server/request'

const FUNC_PATH = "/release"

export default {
    getPage,
    rollback
}


function getPage(searchForm) {
    return request.get(FUNC_PATH+'/getPage', {params: searchForm})
}

function rollback(id) {
    return request.get(FUNC_PATH+'/rollback', {
        params: {'id': id}
    })
}



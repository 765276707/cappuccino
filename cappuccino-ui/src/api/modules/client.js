import request from '@/server/request'

const FUNC_PATH = "/client"

export default {
    getById,
    getPage,
    getConfigInfo,
    save,
    update,
    deleteById,
    clone,
    checkCloneable,
    exports,
    imports
}


function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}

function getPage(searchForm) {
    return request.get(FUNC_PATH+'/getPage', {params: searchForm})
}

function getConfigInfo(id) {
    return request.get(FUNC_PATH+'/getConfigInfo', {
        params: {'id': id}
    })
}

function save(data) {
    return request.post(FUNC_PATH+'/save', data)
}

function update(data) {
    return request.put(FUNC_PATH+'/update', data)
}

function deleteById(id) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: {'id': id}
    })
}

function clone(data) {
    return request.put(FUNC_PATH+'/clone', data)
}

function checkCloneable(data) {
    return request.put(FUNC_PATH+'/checkCloneable', data)
}

function exports(ids) {
    return request.defaults.baseURL + FUNC_PATH + '/exports?ids=' + ids
}

function imports() {
    return request.defaults.baseURL + FUNC_PATH + '/imports'
}
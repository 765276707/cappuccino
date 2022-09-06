import request from '@/server/request'

const FUNC_PATH = "/config"

export default {
    getByClientId,
    save,
    update,
    release,
    deleteById
}


function getByClientId(clientId) {
    return request.get(FUNC_PATH+'/getByClientId', {
        params: {'clientId': clientId}
    })
}

function save(data) {
    return request.post(FUNC_PATH+'/save', data)
}

function update(data) {
    return request.put(FUNC_PATH+'/update', data)
}

function release(id) {
    return request.get(FUNC_PATH+'/release', {
        params: {'id': id}
    })
}

function deleteById(id) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: {'id': id}
    })
}
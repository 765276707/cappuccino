import request from '@/server/request'

const FUNC_PATH = "/grayscale"

export default {
    getByConfigId,
    release,
    releaseFull,
    deleteById
}


function getByConfigId(configId) {
    return request.get(FUNC_PATH+'/getByConfigId', {
        params: {'configId': configId}
    })
}


function release(data) {
    return request.put(FUNC_PATH+'/release', data)
}

function releaseFull(id) {
    return request.get(FUNC_PATH+'/releaseFull', {
        params: {'id': id}
    })
}

function deleteById(id) {
    return request.delete(FUNC_PATH+'/deleteById', {
        params: {'id': id}
    })
}
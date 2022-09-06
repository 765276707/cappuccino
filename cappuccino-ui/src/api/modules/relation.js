import request from '@/server/request'

const FUNC_PATH = "/relation"

export default {
    assign,
    getRelations
}


function getRelations(id) {
    return request.get(FUNC_PATH+'/getRelations', {
        params: {'clientId': id}
    })
}

function assign(data) {
    return request.put(FUNC_PATH+'/assign', data)
}



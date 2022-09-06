import request from '@/server/request'

const FUNC_PATH = "/group"

export default {
    getList,
    getById,
    getPage,
    save,
    update,
    deleteById
}

/**
 * 查询列表 
 */
function getList() {
    return request.get(FUNC_PATH+'/getList')
}


function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}

function getPage(searchText) {
    return request.get(FUNC_PATH+'/getPage', {params: {'searchText': searchText}})
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
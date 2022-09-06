import request from '@/server/request'

const FUNC_PATH = "/template"

export default {
    getById,
    getList,
    getPage,
    save,
    update,
    deleteById
}


function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}

function getList(searchText) {
    return request.get(FUNC_PATH+'/getList', {params: {'searchText': searchText}})
}

function getPage(searchForm) {
    return request.get(FUNC_PATH+'/getPage', {params: searchForm})
}


function save(data) {
    return request.post(FUNC_PATH+'/save', data)
}

function update(data) {
    return request.put(FUNC_PATH+'/update', data)
}

function deleteById(ids) {
    return request.put(FUNC_PATH+'/deleteById', ids)
}

import request from '@/server/request'

const FUNC_PATH = "/user"

export default {
    getList,
    getById,
    getPage,
    save,
    update,
    deleteById,
    resetPassword,
    updateSelf,
    updatePassword
}



function getById(id) {
    return request.get(FUNC_PATH+'/getById', {
        params: {'id': id}
    })
}


function getList(searchText) {
    return request.get(FUNC_PATH+'/getList', {params: {
        'searchText': searchText
    }})
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


function resetPassword(id) {
    return request.get(FUNC_PATH+'/resetPassword', {
        params: {'id': id}
    })
}

function updateSelf(data) {
    return request.put(FUNC_PATH+'/updateSelf', data)
}

function updatePassword(data) {
    return request.put(FUNC_PATH+'/updatePassword', data)
}
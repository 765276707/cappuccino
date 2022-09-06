import request from '@/server/request'

const FUNC_PATH = "/monitor"

export default {
    getInstances,
    getGrayscaleInstances
}


function getInstances(clientId) {
    return request.get(FUNC_PATH+'/getClientInstances', {
        params: {
            'clientId': clientId,
            'isGrayscale': false
        }
    })
}

function getGrayscaleInstances(clientId) {
    return request.get(FUNC_PATH+'/getClientInstances', {
        params: {
            'clientId': clientId,
            'isGrayscale': true
        }
    })
}
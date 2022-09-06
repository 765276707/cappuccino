/**
 * js 防抖，在指定时间范围内，只执行最后一次
 * @param {*} callable 执行的函数
 * @param {*} delayTime 延迟时间
 * @returns 
 */
 export function debounce(callback, delayTime) {
    let timer = null

    return function(...args) {
        if (timer != null) {
            clearTimeout(timer)
        }

        timer = setTimeout(() => {
            callback && callback.apply(this, args)
        }, delayTime)
    }
}
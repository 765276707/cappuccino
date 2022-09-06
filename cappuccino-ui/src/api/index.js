/**
 * 后台Http接口地址统一入口
 * @author Pristine Xu 
 * @create 2021-12-16
 */
import auth from './modules/auth'
import user from './modules/user'
import verifycode from './modules/verifycode'
import environment from './modules/environment'
import group from './modules/group'
import client from './modules/client'
import config from './modules/config'
import grayscale from './modules/grayscale'
import release from './modules/release'
import monitor from './modules/monitor'
import tpl from './modules/template'
import relation from './modules/relation'
import oplog from './modules/oplog'
import auditlog from './modules/auditlog'
import cluster from './modules/cluster'


export default {
    auth,           // 登录接口
    user,           // 用户管理接口
    verifycode,     // 验证码模块
    environment,    // 环境接口
    group,          // 分组接口
    client,         // 客户端接口
    config,         // 配置
    grayscale,      // 灰度接口
    release,        // 历史版本
    monitor,        // 监听
    tpl,            // 模板仓库
    relation,       // 资源授权关系
    oplog,          // 操作日志
    auditlog,       // 审计日志
    cluster         // 集群管理
}
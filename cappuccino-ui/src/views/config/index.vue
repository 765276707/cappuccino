<template>
  <el-container >
    <el-aside width="300px">
        <el-card shadow="always">
            <el-descriptions class="margin-top" title="客户端详情" :column="1" size="medium" v-if="!loading">
                <template slot="extra">
                    <el-button type="info" icon="el-icon-arrow-left" size="small" round @click="backClientPage()">返回</el-button>
                </template>
                <el-descriptions-item label="客户端名">{{ client.clientName }}</el-descriptions-item>
                <el-descriptions-item label="所属环境">{{ client.envName }}</el-descriptions-item>
                <el-descriptions-item label="所属分组">{{ client.groupName }}</el-descriptions-item>
                <el-descriptions-item label="描述">{{ client.clientDesc }}</el-descriptions-item>
                <el-descriptions-item label="负责人">{{ client.charger }}</el-descriptions-item>
                <el-descriptions-item label="负责人电话">{{ client.chargerPhoneNumber }}</el-descriptions-item>
            </el-descriptions>
            <el-skeleton :loading="loading" :rows="6" animated></el-skeleton>
        </el-card>

        <el-card shadow="always" body-style="padding-left: 10px;">
            <div slot="header" class="clearfix">
                <span>最近操作</span>
                <span style="float: right; padding: 3px 0; font-size: 12px; color:#E6A23C;">只保留最近3个月内操作记录</span>
            </div>
            <div v-if="!loading">
                <el-timeline :reverse="reverse" v-if="logs.length" style="padding-left: 0px;">
                    <el-timeline-item
                    v-for="log in logs"
                    :key="log.id"
                    :color="log.color"
                    :timestamp="log.opTime">
                    
                        <span style="font-size: 13px; color: #33CCCC;">{{log.opUser}}</span> <span style="font-size: 13px;">{{log.opDesc}}</span>
                    </el-timeline-item>
                </el-timeline>
                <el-empty description="该客户端最近没有操作记录" image-size="115" v-else></el-empty>
            </div>
            <el-skeleton :loading="loading" :rows="8" animated></el-skeleton>
        </el-card>
    </el-aside>

    <el-container>
        <el-header class="topbar">
            <div class="left-panel" v-if="!loading">
                <span>主配置：<span style="color:#67C23A;" v-if="hasConfig">已配置</span> <span style="color:#E6A23C;" v-else>无</span></span>
                <span style="margin-left: 80px;">灰度配置：<span style="color:#67C23A;" v-if="hasGrayscaleConfig">已配置</span> <span style="color:#E6A23C;" v-else>无</span></span>
            </div>
            <el-skeleton :loading="loading" animated></el-skeleton>
        </el-header>
        <el-main>
            <el-tabs v-model="activeName" type="border-card" style="min-height: 580px;">
                <el-tab-pane name="master">
                    <span slot="label"><i class="el-icon-tickets"></i> 主配置</span>
                    <div v-if="!loading">
                        <el-empty description="当前客户端尚未有任何主配置" v-if="!hasConfig">
                            <el-button type="success" icon="el-icon-plus" @click="save()">发布新的配置</el-button>
                            <el-button type="warning" icon="el-icon-collection-tag" size="small" @click="history()">从历史版本中回滚</el-button>
                        </el-empty>
                        <div v-else>
                            <el-descriptions title="" :column="2" size="large" direction="horizontal">
                                <template slot="extra">
                                    <el-button type="success" icon="el-icon-guide" size="small" @click="monitor()">监听实例</el-button>
                                    <el-button type="primary" icon="el-icon-edit" size="small" @click="update()">发布新版本</el-button>
                                    <el-button type="info" icon="el-icon-price-tag" size="small" plain @click="grayscale()">创建灰度</el-button>
                                    <el-button type="danger" icon="el-icon-delete" size="small" @click="deleteById()">删除</el-button>
                                </template>
                                <el-descriptions-item label="发布状态" label-class-name="label">
                                    <el-tag size="small" type="success">已发布</el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="配置描述" label-class-name="label">{{ config.description==null?'-':config.description }}</el-descriptions-item>
                                <el-descriptions-item label="版本号" label-class-name="label">
                                    <el-tag size="mini" effect="plain">{{ config.version==null?'-':config.version }}</el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="格式" label-class-name="label">{{ config.fileExtension }}</el-descriptions-item>
                                <el-descriptions-item label="MD5" label-class-name="label">{{ config.sign==null?'-':config.sign }}</el-descriptions-item>
                                <el-descriptions-item label="创建时间" label-class-name="label">{{ config.createTime }}</el-descriptions-item>
                                <el-descriptions-item label="发布时间" label-class-name="label">{{ config.releaseTime == null?'-':config.releaseTime }}</el-descriptions-item>
                                <el-descriptions-item label="发布人" label-class-name="label">{{ config.releaseUser == null?'-':config.releaseUser }}</el-descriptions-item>
                            </el-descriptions>
                            <code-mirror v-model="config.content" :mode="config.fileExtension" :readOnly="true"></code-mirror>
                        </div>
                    </div>
                    <el-skeleton :loading="loading" :rows="16" animated></el-skeleton>

                </el-tab-pane>

                <el-tab-pane name="grayscale">
                    <span slot="label"><i class="el-icon-files"></i> 灰度配置</span>
                    <el-empty description="当前尚未创建任何灰度配置，您可前往主配置中创建灰度" v-if="!hasGrayscaleConfig">
                        <el-button type="success" icon="el-icon-plus" @click="save()">发布新的灰度配置</el-button>
                    </el-empty>
                    
                    <div v-else>
                        <el-descriptions title="" :column="2" size="large" direction="horizontal">
                            <template slot="extra">
                                <el-button type="success" icon="el-icon-guide" size="small" @click="monitorGrayscale()">灰度监听实例</el-button>
                                <el-button type="warning" icon="el-icon-finished" size="small" @click="releaseGrayscale()">灰度发布</el-button>
                                <el-button type="primary" icon="el-icon-finished" size="small" @click="releaseFull()">全量发布</el-button>
                                <el-button type="danger" icon="el-icon-delete" size="small" @click="cancelGrayscale()">取消灰度</el-button>
                            </template>
                            <el-descriptions-item label="发布状态" label-class-name="label">
                                <el-tag size="small" type="success">已发布</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item label="格式" label-class-name="label">{{ grayscaleConfig.fileExtension }}</el-descriptions-item>
                            <el-descriptions-item label="主线版本号" label-class-name="label">
                                <el-tag size="mini" effect="plain">{{ grayscaleConfig.version==null?'-':grayscaleConfig.version }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item label="配置描述" label-class-name="label">{{ grayscaleConfig.description==null?'-':grayscaleConfig.description }}</el-descriptions-item>
                            <el-descriptions-item label="MD5" label-class-name="label">{{ grayscaleConfig.sign==null?'-':grayscaleConfig.sign }}</el-descriptions-item>
                            <el-descriptions-item label="创建时间" label-class-name="label">{{ grayscaleConfig.createTime }}</el-descriptions-item>
                            <el-descriptions-item label="发布时间" label-class-name="label">{{ grayscaleConfig.releaseTime }}</el-descriptions-item>
                            <el-descriptions-item label="灰度规则" label-class-name="label" >
                                {{ grayscaleConfig.rules }}
                            </el-descriptions-item>
                        </el-descriptions>
                        <code-mirror v-model="grayscaleConfig.content" :mode="grayscaleConfig.fileExtension" :readOnly="true"></code-mirror>
                    </div>
                </el-tab-pane>

            </el-tabs>

            
   

            <save-update ref="SaveUpdate" @success="success()"></save-update>

            <grayscale-update ref="GrayscaleUpdate" @success="success()"></grayscale-update>

            <monitor-instances ref="MonitorInstances"></monitor-instances>

        </el-main>


    </el-container>
  </el-container>
</template>

<script>
import CodeMirror from '../commons/codemirror.vue'
import SaveUpdate from './save-update.vue'
import GrayscaleUpdate from './grayscale-update.vue'
import MonitorInstances from './monitor-instances.vue'

export default {
    components: {
        CodeMirror,
        SaveUpdate,
        GrayscaleUpdate,
        MonitorInstances
    },
    data () {
        return {
            loading: false,
            radio: 1,
            hasConfig: false,
            hasGrayscaleConfig: false,
            client: {},
            config: {},
            grayscaleConfig: {},
            logs: [],
            activeName: 'master'
        }
    },
    created() {
        this.getByClientId()
    },
    methods: {
        async getByClientId() {
            this.loading = true
            const clientId = this.$route.query.clientId
            let repsonse = await this.$API.client.getConfigInfo(clientId)
            let configInfo = repsonse.data
            this.client = configInfo.client
            if (configInfo.config != null) {
                this.hasConfig = true
            } else {
                this.hasConfig = false
            }
            if (configInfo.grayscale != null) {
                this.hasGrayscaleConfig = true
            } else {
                this.hasGrayscaleConfig = false
            }
            this.config = configInfo.config
            this.grayscaleConfig = configInfo.grayscale
            this.logs = configInfo.auditlogs
            this.loading = false
        },

        save() {
            const clientId = this.$route.query.clientId
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({'clientId': clientId}) 
            })
        },

        update() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(this.config) 
            })
        },

        success(id) {
            this.getByClientId()
        },

        deleteById() {
            this.$confirm('确认删除主线配置?', '危险操作', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                // 删除主线配置
                this.$API.config.deleteById(this.config.id).then(response => {
                    this.getByClientId()
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                })
            })
        },

        release() {
            this.$confirm('是否将配置发布?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.config.release(this.config.id).then(response => {
                    this.getByClientId()
                    this.$message.success(response.message)
                })


            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消发布'
                })
            })
        },

        grayscale() {
            this.$confirm('是否为该主配置发布一个灰度配置?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                // 创建灰度
                // this.$API.grayscale.create(this.config.id).then(response => {
                //     this.getByClientId()
                //     this.activeName = 'grayscale'
                //     this.$message.success(response.message)
                // })

                this.$nextTick(() => {
                    this.$refs.GrayscaleUpdate.open({
                        'id': null,
                        'clientId': this.config.clientId,
                        'description': this.config.description,
                        'rules': null,
                        'fileExtension': this.config.fileExtension,
                        'content': this.config.content,
                        'configVersion': this.config.version
                    }) 
                })


            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消创建'
                })
            })
        },

        backClientPage() {
            // this.$router.push({ path: '/client'})
            this.$router.back(-1)
        },

        updateGrayscale() {
            this.$nextTick(() => {
                this.$refs.GrayscaleUpdate.open(this.grayscaleConfig) 
            })
        },

        releaseGrayscale() {
            this.$nextTick(() => {
                this.$refs.GrayscaleUpdate.open(this.grayscaleConfig) 
            })
        },

        releaseFull() {
            this.$confirm('全量发布将会把灰度配置内容合并到主线配置中，并移除灰度配置，是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                // 全量发布
                this.$API.grayscale.releaseFull(this.grayscaleConfig.id).then(response => {
                    this.getByClientId()
                    this.activeName = 'master'
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消全量发布'
                })
            })
        },

        cancelGrayscale() {
            this.$confirm('取消灰度将会把灰度配置移除，是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                // 取消灰度，直接删除灰度配置
                this.$API.grayscale.deleteById(this.grayscaleConfig.id).then(response => {
                    this.getByClientId()
                    this.activeName = 'master'
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                })
            })
        },

        monitor() {
            // 监听实例
            const clientId = this.$route.query.clientId
            this.$nextTick(() => {
                this.$refs.MonitorInstances.open(clientId, 'MASTER') 
            })
        },

        monitorGrayscale() {
            // 监听灰度实例
            const clientId = this.$route.query.clientId
            this.$nextTick(() => {
                this.$refs.MonitorInstances.open(clientId, 'GRAYSCALE') 
            })
        }

    }   
}
</script>

<style lang="scss" scoped>
.el-aside {
    margin-top: 20px;
    margin-left: 20px;

    & .el-card {
        margin-bottom: 20px;
    }
}
.bg-gray {
    background-color: #E4E7ED;
}

.label { 
    color: #909399;
}

html body {
    background-color: #909399;
}

.el-main {
    padding-top: 10px;
}

.topbar {
    height: 50px;
    border-bottom: 1px solid #ebeef5; 
    background: #F2F6FC;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    margin-left: 20px;
    margin-right: 20px;

    .left-panel {
        display: flex;
        align-items: center;
    }
}
</style>

<style lang="scss">

</style>
<template>
    <el-container>
        <el-header height="80px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    <div class="left-panel">
                        <el-button icon="el-icon-refresh" @click="refresh">刷新</el-button>
                    </div>
                    <div class="right-panel">
                        <div class="right-panel-search">
                            <el-select v-model="searchForm.type" placeholder="配置类型" style="width: 150px;" clearable>
                                <el-option label="主配置" value="1"></el-option>
                                <el-option label="灰度配置" value="2"></el-option>
                            </el-select>
                            <el-date-picker
                            v-model="opTimeRanges"
                            type="daterange"
                            @change="setTime"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :default-time="['00:00:00', '23:59:59']"
                            clearable="">
                            </el-date-picker>
                            <el-input v-model="searchForm.searchText" placeholder="客户端编号/名称" style="width: 200px;"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="getPage()"></el-button>
                        </div>
                    </div>
                </div>
            </div>       
        </el-header>

        <el-main style="padding-left: 10px; padding-right: 10px;">
            <el-card shadow="always" body-style="padding: 0px; min-height: 500px;">
                <el-table
                ref="Table"
                :data="pageResult.list"
                element-loading-text="加载数据中"
                v-loading="loading"
                :header-row-style="{height:45+'px'}"
                :row-style="{height:60+'px'}"
                style="width: 100%; min-height: 450px;"
                :header-cell-style="{background:'#F2F6FC'}">
                    <el-table-column prop="id"          label="序号"    width="100" ></el-table-column>
                    <el-table-column label="配置类型" >
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.type === 1">主配置</el-tag>
                            <el-tag v-else type="info">灰度配置</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="clientId"  label="客户端编号" ></el-table-column>
                    <el-table-column prop="clientName"  label="客户端名称" ></el-table-column>
                    <el-table-column prop="description"  label="配置描述" ></el-table-column>
                    <el-table-column prop="fileExtension"     label="拓展名" ></el-table-column>
                    <el-table-column prop="sign"   label="MD5签名" ></el-table-column>
                    <el-table-column prop="version"   label="版本" width="80"></el-table-column>
                    <el-table-column prop="configVersion"   label="来源主版本(灰度)" width="120"></el-table-column>
                    <el-table-column prop="rules"   label="规则(灰度)"></el-table-column>
                    <el-table-column prop="releaseTime"  label="发布时间" ></el-table-column>
                    <el-table-column prop="releaseUser"  label="发布账号" ></el-table-column>
                    <el-table-column label="操作" width="130">
                        <template slot-scope="scope">
                            <el-button
                                type="text"
                                style="color: #67C23A"
                                icon="el-icon-view"
                                @click="views(scope.$index, scope.row)">查看</el-button>   
                            <el-button
                                type="text"
                                style="color: #E6A23C"
                                icon="el-icon-refresh-left"
                                @click="rollback(scope.$index, scope.row)">回滚</el-button>    
                        </template>
                    </el-table-column>
                    <template #empty>
                        <el-empty :description="'查无数据'" :image-size="100"></el-empty>
                    </template>
                </el-table>
                <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
            </el-card>
            
        </el-main>

        <el-dialog 
            title="查看配置" 
            :visible.sync="visibleViewsDialog"
            v-if="visibleViewsDialog"
            width="1000px"
            :show-close="true"
            @close="closeViews()"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            append-to-body>
                        
            <el-descriptions direction="vertical" :column="1" border>
                <el-descriptions-item label="版本号">{{ currentRow.version==null?'-':currentRow.version }}</el-descriptions-item>
                <el-descriptions-item label="配置">
                    <code-mirror v-model="currentRow.content" :mode="currentRow.fileExtension"></code-mirror>
                </el-descriptions-item>
            </el-descriptions>

        </el-dialog>

    </el-container>
</template>

<script>
import CodeMirror from '../commons/codemirror.vue'

export default {
    components: {
        CodeMirror
    },
    data() {
        return {
            opTimeRanges: null,
            searchForm: {
                type: null,
                searchText: '',
                bgnTime: null,
                endTime: null
            },
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false,
            visibleViewsDialog: false,
            currentRow: {
                fileExtension: null,
                content: null,
                version: null
            },
        }
    },
    mounted() {       
        // 初始化列表
        this.getPage()
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getPage() {
            this.loading = true
            let response = await this.$API.release.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false         
        },

        refresh() {
            this.searchForm.searchText = ''
            this.searchForm.bgnTime = null
            this.searchForm.endTime = null
            this.searchForm.type = null
            this.opTimeRanges = null
            this.getPage()
        },

        /**
         * @title 更改分页
         */
        pageChange(pageNum, pageSize) {
            this.searchForm.pageNum = pageNum
            this.searchForm.pageSize = pageSize
            this.getPage()
        },

        setTime(val) {
            this.searchForm.bgnTime = val[0]
            this.searchForm.endTime = val[1]
        },

        views(index, row) {
            this.visibleViewsDialog = true
            this.currentRow.fileExtension = row.fileExtension
            this.currentRow.content = row.content
            this.currentRow.version = row.version
        },

        closeViews() {
            this.visibleViewsDialog = false
            this.currentRow.fileExtension = null
            this.currentRow.content = null
            this.currentRow.version = null
        },

        rollback(index, row) {
            this.$confirm('回滚将会覆盖主配置并以此发布一个新的版本，是否继续?', '版本回滚', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                // 回滚
                this.$API.release.rollback(row.id).then(response => {
                    this.$emit('success')
                    this.$message.success(response.message)
                    this.close()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消回滚'
                })
            })
        }
       
    }
}
</script>

<style lang="scss" scoped>
.el-main {
    width: 100%;
    height: 100%;
    padding: 0px;
}
.topbar {
    height: 50px; 
    display: flex;
    justify-content:space-between;
    margin-bottom: 0px;

    .left-panel {
        display: flex;
        align-items: center;
    }
    .right-panel {
        display: flex;
        align-items: center;
    }

    .right-panel-search {display: flex;align-items: center;}

    .right-panel-search > * + * {margin-left:10px;}
}


</style>
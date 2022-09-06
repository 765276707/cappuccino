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
                            <env-select v-model="searchForm.envId" placeholder="环境" style="width: 100px;" clearable></env-select>
                            <group-select v-model="searchForm.groupId" placeholder="分组" clearable></group-select>
                            <el-select v-model="searchForm.opType" placeholder="操作类型" style="width: 100px;" clearable>
                                <el-option label="创建配置" value="创建配置"></el-option>
                                <el-option label="发布配置" value="发布配置"></el-option>
                                <el-option label="回滚配置" value="回滚配置"></el-option>
                                <el-option label="删除配置" value="删除配置"></el-option>
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
                            <el-input v-model="searchForm.searchText" placeholder="客户端/操作描述/操作者" style="width: 200px;"></el-input>
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
                    <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                    <el-table-column prop="clientName"  label="客户端" ></el-table-column>
                    <el-table-column prop="envName"  label="环境"></el-table-column>
                    <el-table-column prop="groupName"  label="分组" ></el-table-column>
                    <el-table-column label="操作类型" >
                        <template slot-scope="scope">
                            <span :style="'color:' + scope.row.color">{{scope.row.opType}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="配置类型" >
                        <template slot-scope="scope">
                            {{ scope.row.configType===1?'主':'灰' }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="opDesc"  label="操作描述" width="250"></el-table-column>
                    <el-table-column prop="opUser"  label="操作者" ></el-table-column>
                    <el-table-column prop="opTime"  label="操作时间" ></el-table-column>
                    <template #empty>
                        <el-empty :description="'查无数据'" :image-size="100"></el-empty>
                    </template>
                </el-table>
                <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
            </el-card>
            
        </el-main>

    </el-container>
</template>

<script>
import EnvSelect from '../commons/env-select.vue'
import GroupSelect from '../commons/group-select.vue'

export default {
    components: {
        EnvSelect,
        GroupSelect
    },
    data() {
        return {
            opTimeRanges: null,
            searchForm: {
                opType: null,
                searchText: '',
                bgnTime: null,
                endTime: null,
                envId: null,
                groupId: null
            },
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false
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
            let response = await this.$API.auditlog.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false         
        },

        refresh() {
            this.searchForm.searchText = ''
            this.searchForm.bgnTime = null
            this.searchForm.endTime = null
            this.searchForm.opType = null
            this.searchForm.envId = null
            this.searchForm.groupId = null
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
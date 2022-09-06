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
                            <el-select v-model="searchForm.opType" placeholder="操作类型" style="width: 100px;" clearable>
                                <el-option label="新增" :value="false"></el-option>
                                <el-option label="更新" :value="true"></el-option>
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
                            <el-input v-model="searchForm.searchText" placeholder="操作描述/操作者" style="width: 200px;"></el-input>
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
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <el-descriptions title="参数详情" :column="1" style="padding: 10px; color: #2b4b6b; ">
                                <el-descriptions-item label="请求方法">{{ props.row.opMethod }}</el-descriptions-item>
                                <el-descriptions-item label="请求参数">{{ props.row.opArgs }}</el-descriptions-item>
                            </el-descriptions>
                        </template>
                    </el-table-column>
                    <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                    <el-table-column prop="reqUri"  label="请求路径" ></el-table-column>
                    <el-table-column prop="opMethod"  label="请求方法" ></el-table-column>
                    <el-table-column prop="opDesc"  label="操作描述" ></el-table-column>
                    <!-- <el-table-column prop="opArgs"  label="请求参数" ></el-table-column> -->
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

export default {
    data() {
        return {
            opTimeRanges: null,
            searchForm: {
                opType: null,
                searchText: '',
                bgnOpTime: null,
                endOpTime: null,
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
            let response = await this.$API.oplog.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false         
        },

        refresh() {
            this.searchForm.searchText = ''
            this.searchForm.bgnOpTime = null
            this.searchForm.endOpTime = null
            this.searchForm.opType = null
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
            this.searchForm.bgnOpTime = val[0]
            this.searchForm.endOpTime = val[1]
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
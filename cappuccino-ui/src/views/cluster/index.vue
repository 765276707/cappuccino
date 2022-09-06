<template>
    <el-container>
        <!-- <el-alert
            v-if="result.clusterProtectionEnabled && result.protectStatus"
            title="当前服务集群中可用节点比例已低于85%，已开启自我保护机制，集群处于不稳定状态"
            type="error"
            center
            :closable="false">
        </el-alert> -->
        <el-header height="80px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    <div class="left-panel" style="font-size: 14px;" v-if="clusterInfo.enabled">
                        集群模式，当前共有 <span style="color: #E6A23C;">&nbsp;{{ clusterInfo.clusters.length }}&nbsp;</span> 个节点
                    </div>
                    <div class="left-panel" style="font-size: 14px;" v-else>
                        单机模式
                    </div>
                    <div class="right-panel">
                        <div class="right-panel-search">
                            <el-input v-model="searchForm.searchText" placeholder="节点地址" style="width: 200px;"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="getInfo()"></el-button>
                            <el-button icon="el-icon-refresh" @click="refresh()"></el-button>
                        </div>
                    </div>
                </div>
            </div>       
        </el-header>

        <el-main style="padding-left: 10px; padding-right: 10px;">
            <el-card shadow="always" body-style="padding: 0px; min-height: 350px;">
                <el-table
                ref="Table"
                :data="clusterInfo.clusters"
                element-loading-text="加载数据中"
                v-loading="loading"
                :header-row-style="{height:45+'px'}"
                :row-style="{height:60+'px'}"
                style="width: 100%; min-height: 350px;"
                :header-cell-style="{background:'#F2F6FC'}">
                    <el-table-column type="index" width="80"></el-table-column>
                    <el-table-column prop="address"     label="节点" ></el-table-column>
                    <el-table-column label="活跃状态">
                        <template slot-scope="scope">
                            <el-tag type="success" size="mini" v-if="scope.row.alive">UP</el-tag>
                            <el-tag type="danger" size="mini" v-else>DOWN</el-tag>
                        </template>
                    </el-table-column>
                    <template #empty>
                        <el-empty :description="'当前无节点'" :image-size="100"></el-empty>
                    </template>
                </el-table>
            </el-card>
            
        </el-main>

    </el-container>
</template>

<script>

export default {
    data() {
        return {
            loading: false,
            searchForm: {
                searchText: ''
            },
            clusterInfo: {
                enabled: null,
                clusters: []
            },
            timer: null 
        }
    },
    mounted() {       
        // 初始化列表
        this.getInfo()
        this.timer = setInterval(this.getInfo, 30000);
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getInfo() {
            this.loading = true
            let response = await this.$API.cluster.getInfo()   
            this.clusterInfo = response.data
            this.loading = false         
        },

        refresh() {
            this.searchForm.searchText = ''
            this.getInfo()
        },
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
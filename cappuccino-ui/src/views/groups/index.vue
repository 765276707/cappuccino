<template>
    <el-container>
        <!-- <el-header class="topbar">
            <div class="left-panel">
                <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                <el-button type="warning" icon="el-icon-refresh" @click="refresh">刷新</el-button>
            </div>
            <div class="right-panel">
                <div class="right-panel-search">
                    <el-input v-model="searchText" placeholder="编号/名称/类型"></el-input>
                    <el-button type="primary" icon="el-icon-search" @click="getPage"></el-button>
                </div>
            </div>
        </el-header> -->
        <el-header height="80px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    <div class="left-panel">
                        <el-button type="success" icon="el-icon-plus" @click="save"  v-permission="['SA']">添加</el-button>
                        <el-button icon="el-icon-refresh" @click="refresh">刷新</el-button>
                        <el-button type="danger" icon="el-icon-delete" @click="deleteSelectedRows"  v-permission="['SA']">删除</el-button>
                    </div>
                    <div class="right-panel">
                        <div class="right-panel-search">
                            <el-input v-model="searchForm.searchText" placeholder="编号/名称" style="width: 200px;"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="getPage()"></el-button>
                            <!-- <el-popover width="60" trigger="click">
                                <el-checkbox-group v-model="columnSelecteds">
                                    <el-checkbox v-for="item in columnHeaders" :label="item.prop" :key="item.prop" >{{item.label}}</el-checkbox>    
                                </el-checkbox-group>
                                <el-button slot="reference" icon="el-icon-s-grid"></el-button>
                            </el-popover> -->
                        </div>
                    </div>
                </div>
            </div>       
        </el-header>

        <el-main style="padding-left: 10px; padding-right: 10px;">
            <el-card shadow="always" body-style="padding: 0px; min-height: 500px;">
                <el-table
                :data="pageResult.list"
                element-loading-text="加载数据中"
                v-loading="loading"
                :header-row-style="{height:45+'px'}"
                :header-cell-style="{background:'#F2F6FC'}"
                :row-style="{height:60+'px'}"
                style="width: 100%; min-height: 450px;">
                    <el-table-column type="selection"   width="55" ></el-table-column>
                    <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                    <el-table-column prop="groupName"  label="分组名称" ></el-table-column>
                    <el-table-column prop="groupDesc"  label="分组描述" ></el-table-column>
                    <el-table-column prop="orderNum"  label="排序号" ></el-table-column>
                    <el-table-column label="是否允许修改" >
                        <template slot-scope="scope">
                            <el-tag type="success" size="mini" v-if="scope.row.enableWrite">允许</el-tag>
                            <el-tag type="danger" size="mini" v-else>禁止</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime"  label="创建时间" ></el-table-column>
                    <el-table-column prop="updateTime"  label="修改时间" ></el-table-column>
                    <el-table-column label="操作" width="220">
                        <template slot-scope="scope">
                            <el-button
                                v-permission="['SA']"
                                v-show="scope.row.enableWrite"
                                type="text"
                                icon="el-icon-edit"
                                @click="update(scope.$index, scope.row)">编辑</el-button>
                            <el-button
                                v-permission="['SA']"
                                v-show="scope.row.enableWrite"
                                type="text"
                                style="color: #F56C6C"
                                icon="el-icon-delete"
                                @click="deleteInRow(scope.$index, scope.row)">删除</el-button>        
                        </template>
                    </el-table-column>
                    <template #empty>
                        <el-empty :description="'暂无数据'" :image-size="100"></el-empty>
                    </template>
                </el-table>
                <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
            </el-card>
        </el-main>

        <save-update ref="SaveUpdate" @success="getPage"></save-update>

    </el-container>
</template>

<script>
import SaveUpdate from './save-update.vue'
import permission from '@/directive/vperm/index.js'

export default {
    directives: {
        permission
    },
    data() {
        return {        
            searchForm: {
                searchText: ''
            },
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false,
        }
    },
    components: {
        SaveUpdate
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
            let response = await this.$API.group.getPage(this.searchText)   
            this.pageResult = response.data
            this.loading = false
        },

        refresh() {
            this.searchText = ''
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

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({}) 
            })
        },

        update(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row) 
            })
        },

    
        deleteInRow(index, row) {
            this.$confirm('此操作将会删除该分组，是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.group.deleteById(row.id).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
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
    padding: 0;
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
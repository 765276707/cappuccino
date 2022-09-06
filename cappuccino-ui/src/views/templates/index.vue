<template>
    <el-container>
        <el-header height="80px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    <div class="left-panel">
                        <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                        <el-button icon="el-icon-refresh" @click="refresh">刷新</el-button>
                        <el-button type="danger" icon="el-icon-delete" @click="deleteSelectedRows">删除</el-button>
                    </div>
                    <div class="right-panel">
                        <div class="right-panel-search">
                            <el-select v-model="searchForm.disabled" placeholder="启用状态" style="width: 100px;" clearable>
                                <el-option label="启用" :value="false"></el-option>
                                <el-option label="禁用" :value="true"></el-option>
                            </el-select>
                            <el-input v-model="searchForm.searchText" placeholder="编号/模板名称/模板类型" style="width: 200px;"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="getPage()"></el-button>
                            <el-popover width="60" trigger="click">
                                <el-checkbox-group v-model="columnSelecteds">
                                    <el-checkbox v-for="item in columnHeaders" :label="item.prop" :key="item.prop" >{{item.label}}</el-checkbox>    
                                </el-checkbox-group>
                                <el-button slot="reference" icon="el-icon-s-grid"></el-button>
                            </el-popover>
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
                @selection-change="handleSelectedChange"
                element-loading-text="加载数据中"
                v-loading="loading"
                :header-row-style="{height:45+'px'}"
                :row-style="{height:60+'px'}"
                style="width: 100%; min-height: 450px;"
                :header-cell-style="{background:'#F2F6FC'}">
                    <el-table-column type="selection"   width="55" ></el-table-column>
                    <el-table-column v-if="columnHeaders[0].isShow" :prop="columnHeaders[0].prop" :label="columnHeaders[0].label" width="80" ></el-table-column>
                    <el-table-column v-if="columnHeaders[1].isShow" :prop="columnHeaders[1].prop" :label="columnHeaders[1].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[2].isShow" :prop="columnHeaders[2].prop" :label="columnHeaders[2].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[3].isShow" :prop="columnHeaders[3].prop" :label="columnHeaders[3].label" width="100"></el-table-column>
                    <el-table-column v-if="columnHeaders[4].isShow" :label="columnHeaders[4].label" width="100" >
                        <template slot-scope="scope">
                            <el-tag type="success" size="mini" v-if="!scope.row.disabled">启用</el-tag>
                            <el-tag type="danger" size="mini" v-else>禁用</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="columnHeaders[5].isShow" :prop="columnHeaders[5].prop" :label="columnHeaders[5].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[6].isShow" :prop="columnHeaders[6].prop" :label="columnHeaders[6].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[7].isShow" :prop="columnHeaders[7].prop" :label="columnHeaders[7].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[8].isShow" :prop="columnHeaders[8].prop" :label="columnHeaders[8].label"></el-table-column>
                    <el-table-column label="操作" width="220">
                        <template slot-scope="scope">
                            <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="update(scope.row)">编辑</el-button>
                            <el-button
                                type="text"
                                style="color: #F56C6C"
                                icon="el-icon-delete"
                                @click="deleteInRow(scope.row)">删除</el-button>        
                        </template>
                    </el-table-column>
                    <template #empty>
                        <el-empty :description="'暂无数据'" :image-size="100"></el-empty>
                    </template>
                </el-table>
                
                <pagination :total="pageResult.total" @page-change="pageChange"></pagination>
            </el-card>

            <!-- <el-row :gutter="16">
                <el-col v-for="row in pageResult.list" :key="row.id" 
                    :xs="24"
                    :sm="12"
                    :md="8"
                    :lg="6"
                    :xl="4"
                >
                    <card :row="row" @row-update="update(row)" @row-delete="deleteInRow(row)"></card>
                </el-col>
            </el-row>
            
            <pagination :total="pageResult.total" @page-change="pageChange"></pagination> -->
            
        </el-main>

        <save-update ref="SaveUpdate" @success="getPage"></save-update>

    </el-container>
</template>

<script>
import SaveUpdate from './save-update.vue'

export default {
    data() {
        return {
            searchForm: {
                disabled: null,
                searchText: '',
            },
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false,
            // 当前选中的rows
            currentSelectedRows: [], 
            // 表头
            columnHeaders: [
                {index: 0, prop: 'id', label: "编号", isShow: true},
                {index: 1, prop: 'tplName', label: "模板名称", isShow: true},
                {index: 2, prop: 'tplDesc', label: "模板描述", isShow: true},
                {index: 3, prop: 'tplFileExtension', label: "文件类型", isShow: true},
                {index: 4, prop: 'disabled', label: "是否禁用", isShow: true},
                {index: 5, prop: 'createTime', label: "创建时间", isShow: true},
                {index: 6, prop: 'createUser', label: "创建者", isShow: false},
                {index: 7, prop: 'updateTime', label: "更新时间", isShow: false},
                {index: 8, prop: 'updateUser', label: "更新者", isShow: false},
            ],
            // 已选择的项
            columnSelecteds: ['id', 'tplName', 'tplDesc', 'tplFileExtension', 'disabled', 'createTime', 'updateTime']
        }
    },
    components: {
        SaveUpdate
    },
    watch: {
        /**
         * 监听列显示隐藏
         */
        columnSelecteds(newArrayVal) {
            // 计算为被选中的列标题数组
            var nonSelecteds = this.columnHeaders
                        .filter(item => newArrayVal.indexOf(item.prop) == -1) 
                        .map(item => item.prop)
            // 根据计算结果进行表格重绘
            this.columnHeaders.filter(item => {
                let isNonSelected = nonSelecteds.indexOf(item.prop) != -1
                if (isNonSelected) {
                    // 隐藏未选中的列
                    item.isShow = false
                    this.$nextTick(() => {
                        this.$refs.Table.doLayout()
                    })
                } else {
                    // 显示已选中的列
                    item.isShow = true
                    this.$nextTick(() => {
                        this.$refs.Table.doLayout()
                    })
                }
            })  
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
            let response = await this.$API.tpl.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false
        },

        refresh() {
            this.searchForm.searchText = ''
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

        /**
         * 获取当前多选的记录集
         */
        handleSelectedChange(rows) {
            this.currentSelectedRows = rows
        },

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({}) 
            })
        },

        update(row) {
            this.$nextTick(() => {
                console.log(row)
                this.$refs.SaveUpdate.open(row) 
            })
        },

    
        deleteInRow(row) {
            this.$confirm('此操作将会删除该模板，是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {

                let rowIds = []
                rowIds.push(row.id)
                
                this.$API.tpl.deleteById(rowIds).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })
        },

        deleteSelectedRows() {
            // 判断是否有选中
            if (!this.currentSelectedRows || this.currentSelectedRows.length==0) {
                this.$message({
                    message: '您当前未选中任何模板',
                    type: 'warning'
                })
                return
            }
            this.$confirm('此操作将会删除选中的'+this.currentSelectedRows.length+'个模板，是否继续?', '操作提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {

                let rowIds = this.currentSelectedRows.map(row => {
                    return row.id
                })
                
                this.$API.tpl.deleteById(rowIds).then(response => {
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
<template>
    <el-container>
        <el-header height="80px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    <div class="left-panel">
                        <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                        <el-button type="danger" icon="el-icon-delete" @click="deleteSelectedRows">删除</el-button>
                    </div>
                    <div class="right-panel">
                        <div class="right-panel-search">
                            <el-select v-model="searchForm.role" placeholder="角色" style="width: 130px;" clearable>
                                <el-option label="超级管理员" value="SA"></el-option>
                                <el-option label="普通管理员" value="CA"></el-option>
                            </el-select>
                            <el-input v-model="searchForm.searchText" placeholder="用户名/真实姓名/部门/职位" style="width: 250px;"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="getPage"></el-button>
                            <el-button type="warning" icon="el-icon-refresh" @click="refresh"></el-button>
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
                :data="pageResult.list"
                @selection-change="handleSelectedChange"
                element-loading-text="加载数据中"
                v-loading="loading"
                :header-row-style="{height:45+'px'}"
                :header-cell-style="{background:'#F2F6FC'}"
                :row-style="{height:60+'px'}"
                style="width: 100%; min-height: 450px;">
                    <el-table-column type="selection"   width="55" ></el-table-column>
                    <el-table-column prop="id"          label="编号"    width="100" ></el-table-column>
                    <el-table-column prop="username"    label="用户名" ></el-table-column>
                    <el-table-column prop="realname"    label="真实姓名" ></el-table-column>
                    <el-table-column prop="phoneNumber" label="联系电话" ></el-table-column>
                    <el-table-column prop="email"       label="邮箱地址" ></el-table-column>
                    <el-table-column v-if="columnHeaders[0].isShow" :prop="columnHeaders[0].prop" :label="columnHeaders[0].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[1].isShow" :prop="columnHeaders[1].prop" :label="columnHeaders[1].label"></el-table-column>
                    <el-table-column label="角色" >
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.role === 'SA'" type="success" size="mini">超级管理员</el-tag>
                            <el-tag v-if="scope.row.role === 'CA'" type="primary" size="mini">普通管理员</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="columnHeaders[2].isShow" :prop="columnHeaders[2].prop" :label="columnHeaders[2].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[3].isShow" :prop="columnHeaders[3].prop" :label="columnHeaders[3].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[4].isShow" :prop="columnHeaders[4].prop" :label="columnHeaders[4].label"></el-table-column>
                    <el-table-column v-if="columnHeaders[5].isShow" :prop="columnHeaders[5].prop" :label="columnHeaders[5].label"></el-table-column>
                    <el-table-column label="操作" width="220">
                        <template slot-scope="scope">
                            <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="edit(scope.$index, scope.row)">编辑</el-button>
                            <el-button
                                type="text"
                                icon="el-icon-key"
                                @click="resetPassword(scope.$index, scope.row)">重置密码</el-button>
                            <el-button
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

export default {
    data() {
        return {
            searchForm: {
                role: '',
                searchText: ''
            },
            searchText: '',
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
                {index: 0, prop: 'deptName', label: "部门名称", isShow: true},
                {index: 1, prop: 'jobName', label: "职位名称", isShow: true},
                {index: 2, prop: 'createTime', label: "创建时间", isShow: true},
                {index: 3, prop: 'createUser', label: "创建者", isShow: false},
                {index: 4, prop: 'updateTime', label: "更新时间", isShow: false},
                {index: 5, prop: 'updateUser', label: "更新者", isShow: false},
            ],
            // 已选择的项
            columnSelecteds: ['deptName', 'jobName','createTime']
        }
    },
    components: {
        SaveUpdate
    },
    mounted() {
        // 初始化列表
        this.getPage()
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
    methods: {
        /**
         * @title 获取列表
         */
        async getPage() {
            this.loading = true
            let response = await this.$API.user.getPage(this.searchForm)   
            this.pageResult = response.data
            this.loading = false
        },

        refresh() {
            this.searchForm.role = ''
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
                this.$refs.SaveUpdate.open({
                    gender: '男',
                    status: 1,
                    role: 'CA'
                }) 
            })
        },

        edit(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row) 
            })
        },

        resetPassword(index, row) {
            this.$confirm('此操作将会重置用户【'+row.username+'】的密码，是否继续?', '重置用户密码', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.user.resetPassword(row.id).then(response => {
                    this.getPage()
                    this.$message.success(response.message)
                })


            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消重置'
                })
            })
        },

        deleteInRow(index, row) {
            this.$confirm('此操作将会删除该【'+row.username+'】用户，是否继续?', '删除用户', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                let rowIds = []
                rowIds.push(row.id)

                this.$API.user.deleteById(rowIds).then(response => {
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
            // 判断是否有选中，是否大于10个用户
            if (!this.currentSelectedRows || this.currentSelectedRows.length==0) {
                this.$message({
                    message: '您当前未选中任何用户',
                    type: 'warning'
                })
                return
            }
            if (this.currentSelectedRows.length > 10) {
                this.$message({
                    message: '单次最多同时删除10个用户',
                    type: 'warning'
                })
                return
            }
            // 删除信息
            let delInfo = '';
            this.currentSelectedRows.forEach(e => {
                delInfo = delInfo + '【' + e.username + '】 '
            })
            this.$confirm('此操作将会删除选中的'+delInfo+'用户，是否继续?', '删除用户', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {

                let rowIds = this.currentSelectedRows.map(row => {
                    return row.id
                })
                
                this.$API.user.deleteById(rowIds).then(response => {
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

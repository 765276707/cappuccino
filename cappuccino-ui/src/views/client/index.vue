<template>
    <el-container>
        <!-- 环境选择 -->
        <el-header height="80px" style="background-color: #F2F6FC; margin: 10px; height: 60px; border-radius: 5px; padding-top: 15px;" >
            <el-form :inline="true">
                <el-form-item label="客户端环境">
                    <el-radio-group v-model="searchForm.envId" size="mini" @change="envChange()" class="cc-radio-button">
                        <el-radio-button label="1">PUBLIC (公共环境)</el-radio-button>
                        <el-radio-button label="2">PRODUCTION (生产环境)</el-radio-button>
                        <el-radio-button label="3">DEVELOPMENT (开发环境)</el-radio-button>
                        <el-radio-button label="4">TESTS (测试环境)</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-tag type="warning" size="medium">* 各个环境之间相互隔离</el-tag>
                </el-form-item>
            </el-form>
        </el-header>
        
        <el-container>
            <el-aside width="250px" class="tree-box" style="padding-left: 10px; padding-right: 5px;">
                <el-card shadow="always" body-style="padding: 0px; min-height: 300px; max-height: 600px;">
                    <div style="height: 45px; line-height: 45px; background-color: #F2F6FC;">
                        <span style="margin-left: 10px; font-szie: 16px; color: #606266;"><b>分组</b></span>
                    </div>
                    <el-input
                        placeholder="请输入分组关键字过滤"
                        v-model="filterText"
                        clearable>
                    </el-input>
                    <el-tree 
                        ref='Tree'
                        :data="groups" 
                        :highlight-current="true" 
                        node-key="value" 
                        @node-click="handleNodeClick" 
                        :filter-node-method="filterNode"
                        :current-node-key="searchForm.groupId">
                    </el-tree>
                </el-card>
            </el-aside>

            <el-main style="padding-left: 5px; padding-right: 10px;">
                <el-card shadow="always" body-style="padding: 0px; min-height: 500px;">
                    <el-container>
                        <el-header class="topbar">
                            <div class="left-panel">
                                <el-button type="success" icon="el-icon-plus" @click="save">添加</el-button>
                                <el-button style="background-color: #9400D3; color: #fff;" icon="el-icon-document-copy" @click="cloneMany">克隆</el-button>
                                <el-button style="background-color: #00CED1; color: #fff;" icon="el-icon-upload2" @click="imports">导入</el-button>
                                <el-button style="background-color: #00CED1; color: #fff;" icon="el-icon-download" @click="exports">导出</el-button>
                                <el-button type="danger" icon="el-icon-delete">删除</el-button>

                            </div>
                            <div class="right-panel">
                                <div class="right-panel-search">
                                    <el-input v-model="searchForm.searchText" placeholder="编号/名称/类型"></el-input>
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
                        </el-header>

                        <el-main>
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
                                <el-table-column prop="clientName"  label="客户端名称" ></el-table-column>
                                <el-table-column prop="clientDesc"  label="客户端描述" ></el-table-column>
                                <el-table-column v-if="columnHeaders[0].isShow" :prop="columnHeaders[0].prop" :label="columnHeaders[0].label"></el-table-column>
                                <el-table-column v-if="columnHeaders[1].isShow" :prop="columnHeaders[1].prop" :label="columnHeaders[1].label"></el-table-column>
                                <el-table-column prop="charger"  label="负责人" ></el-table-column>
                                <el-table-column prop="chargerPhoneNumber"  label="负责人电话" ></el-table-column>
                                <el-table-column v-if="columnHeaders[2].isShow" :prop="columnHeaders[2].prop" :label="columnHeaders[2].label"></el-table-column>
                                <el-table-column v-if="columnHeaders[3].isShow" :prop="columnHeaders[3].prop" :label="columnHeaders[3].label"></el-table-column>
                                <el-table-column v-if="columnHeaders[4].isShow" :prop="columnHeaders[4].prop" :label="columnHeaders[4].label"></el-table-column>
                                <el-table-column v-if="columnHeaders[5].isShow" :prop="columnHeaders[5].prop" :label="columnHeaders[5].label"></el-table-column>
                                <el-table-column label="操作" width="220">
                                    <template slot-scope="scope">
                                        <el-button
                                            type="text"
                                            style="color: #00CED1"
                                            icon="el-icon-tickets"
                                            @click="config(scope.$index, scope.row)">管理配置</el-button>
                                        <el-button
                                            type="text"
                                            style="color: #9400D3"
                                            icon="el-icon-document-copy"
                                            @click="clone(scope.$index, scope.row)">克隆</el-button> 
                                        <el-button
                                            type="text"
                                            style="color: #E6A23C"
                                            icon="el-icon-finished"
                                            v-permission="['SA']"
                                            @click="assign(scope.$index, scope.row)">授权</el-button>   
                                        <el-button
                                            type="text"
                                            icon="el-icon-edit"
                                            @click="update(scope.$index, scope.row)">编辑</el-button>
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
                        </el-main>

                        <save-update ref="SaveUpdate" @success="getPage"></save-update>
                        <assign ref="Assign"></assign>
                        <clone ref="Clone"></clone>

                    </el-container>
                </el-card>    
            </el-main>
        </el-container>

        <!--
            部门导出
        -->
        <import 
            :visible="importParam.visible" 
            @import-complete="importComplete()">
        </import>

    </el-container>


</template>

<script>
import SaveUpdate from './save-update.vue'
import Assign from './assign.vue'
import permission from '@/directive/vperm/index.js'
import Clone from './clone.vue'
import Import from './import.vue'

export default {
    directives: {
        permission
    },
    data() {
        return {   
            searchForm: {
                searchText: '',
                envId: 1,
                groupId: 1
            },
            filterText: '', // 树的过滤条件
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false,
            env: 'PUBLIC',
            groups: [],
            currentSelectedRows: [], // 当前选中的rows
            // 表头
            columnHeaders: [
                {index: 0, prop: 'envName', label: "所属环境", isShow: false},
                {index: 1, prop: 'groupName', label: "所属分组", isShow: false},
                {index: 2, prop: 'createTime', label: "创建时间", isShow: true},
                {index: 3, prop: 'createUser', label: "创建者", isShow: true},
                {index: 4, prop: 'updateTime', label: "更新时间", isShow: false},
                {index: 5, prop: 'updateUser', label: "更新者", isShow: false},
            ],
            // 已选择的项
            columnSelecteds: ['createTime', 'createUser'],
            importParam: {
                visible: false //导入组件是否可见
            }
        }
    },
    components: {
        SaveUpdate,
        Assign,
        Clone,
        Import
    },
    mounted() {
        // 初始化环境
        // 初始化分组
        this.getGroupList()
        // 初始化列表
        this.getPage()
    },
    watch: {
        /**
         * 树数据过滤
         */
        filterText(val) {
            this.$refs.Tree.filter(val);
        },
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
            try {
                let response = await this.$API.client.getPage(this.searchForm)   
                this.pageResult = response.data
            } catch(err) {
                this.loading = false
            }
            this.loading = false
        },

        async getGroupList() {
            let response = await this.$API.group.getList()
            this.groups = response.data
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

        /**
         * 获取当前多选的记录集
         */
        handleSelectedChange(rows) {
            this.currentSelectedRows = rows
        },

        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },

        save() {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open({
                    'envId': this.searchForm.envId,
                    'groupId': this.searchForm.groupId
                }) 
            })
        },

        config(index, row) {
            this.$router.push({ path: '/config', query: { clientId: row.id }})
        },

        update(index, row) {
            this.$nextTick(() => {
                this.$refs.SaveUpdate.open(row) 
            })
        },

        assign(index, row) {
            
            this.$nextTick(() => {
                this.$refs.Assign.open(row) 
            })
        },

        clone(index, row) {
            this.$nextTick(() => {
                let rows = []
                rows.push(row)
                this.$refs.Clone.open(rows) 
            })
        },

        cloneMany() {
            // 判断是否有选中
            if (!this.currentSelectedRows || this.currentSelectedRows.length==0) {
                this.$message({
                    message: '您当前未选中任何客户端',
                    type: 'warning'
                })
                return
            }
            this.$nextTick(() => {
                this.$refs.Clone.open(this.currentSelectedRows) 
            })
        },

        imports() {
            this.importParam.visible = true
        },

        /**
         * @title 导入完成
         */
        importComplete() {
            this.importParam.visible = false
            // 更新列表数据
            this.getPage()
        },

        exports() {
            // 判断是否有选中
            if (!this.currentSelectedRows || this.currentSelectedRows.length==0) {
                this.$message({
                    message: '您当前未选中任何客户端',
                    type: 'warning'
                })
                return
            }
            this.$confirm('是否导出选中的客户端?', '导出提示', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                let ids = this.currentSelectedRows.map(e => {
                    return e.id
                })

                let strIds = ids.join()

                window.location.href = this.$API.client.exports(strIds)


            }).catch(error => {
                console.log(error)
                this.$message({
                    type: 'info',
                    message: '已取消导出'
                })
            })
        },

        deleteInRow(index, row) {
            this.$confirm('此操作将会删除该客户端，仅限超级管理员进行操作，是否继续?', '危险操作', {
                confirmButtonText: '继续',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                
                this.$API.client.deleteById(row.id).then(response => {
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

        handleNodeClick(data) {
            this.searchForm.groupId = data.value
            this.getPage()
        },

        envChange() {
            this.getPage()
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
    border-bottom: 1px solid #ebeef5; 
    background: #F2F6FC;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
    display: flex;
    justify-content:space-between;

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

.tree-box >>> .el-tree-node {
    line-height: 45px;
}
.tree-box >>> .el-tree-node__content{
    height: 45px;
    line-height: 45px;
}

</style>

<style lang="scss">
/* 点击树结构项的选中颜色 */
.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
   background-color:  hsl(199, 31%, 55%);
   color: #ebeef5;
}



body > .el-container {
    margin-bottom: 40px;
    height: 100%;
}

body html{
    height: 100%;
}
</style>
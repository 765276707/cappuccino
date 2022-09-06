<template>
  <div>
    <!-- 导航栏的图标 -->
    <el-button style="padding: 8px 10px;" size="small" type="danger" @click.native="open()">
        <svg-icon icon-class="scripts" />
    </el-button>

    <!-- 展示可用的模板外层对话框 -->
    <el-dialog :visible.sync="dialogTableVisible" width="600px" append-to-body>
        <div slot="title">
            <span style="padding-right: 10px;">模板仓库</span>
        </div>
        <div id="search" style="display: flex;align-items: center; margin-bottom: 10px;">
            <el-input v-model="searchForm.searchText" placeholder="模板名称 / 文件类型" style="margin-right: 10px;"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="getPage()"></el-button>
            <el-button type="warning" icon="el-icon-refresh" @click="refresh()"></el-button>
        </div>
        <div id="result" v-if="!loading">
            <div v-if="pageResult.list && pageResult.list.length">
                <div v-for="item in pageResult.list" :key="item.id" style="padding: 10px; background-color: hsl(199, 31%, 55%); color: #F0FFF0; margin-bottom: 3px; border-radius:5px;">
                    <div style="float: left;">
                        <i class="el-icon-tickets"></i> {{ item.tplName }}  
                        <el-tag type="warning" size="mini">{{item.tplFileExtension}}</el-tag>
                    </div>
                    <div style="text-align: right;">
                        <el-button type="success" size="mini" @click.native="view(item)">查看</el-button>
                        <el-button type="primary" size="mini" @click.native="copy(item.tplContent)" class="copy-btn">复制</el-button>
                    </div>
                </div> 
                <el-pagination
                    small
                    layout="prev, pager, next"
                    @current-change="pageChange"
                    :current-page="pageResult.pageNum"
                    :page-size="pageResult.pageSize"
                    :total="pageResult.total"
                    style="text-align: center;">
                </el-pagination> 
            </div>
            <el-empty description="无匹配的内容" v-else></el-empty>
        </div>
        <el-skeleton :loading="loading" animated></el-skeleton>
    </el-dialog>

    <!-- 预览配置内容对话框 -->
    <el-dialog 
        title="查看配置" 
        :visible.sync="dialogViewsVisible"
        v-if="dialogViewsVisible"
        width="800px"
        :show-close="true"
        @close="closeViews()"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
                    
        <el-descriptions direction="vertical" :column="1" border>
            <el-descriptions-item label="文件类型">{{ currentTemplate.tplFileExtension==null?'-':currentTemplate.tplFileExtension }}</el-descriptions-item>
            <el-descriptions-item label="配置内容">
                <code-mirror v-model="currentTemplate.tplContent" :mode="currentTemplate.tplFileExtension" style="width: 750px;"></code-mirror>
            </el-descriptions-item>
        </el-descriptions>

    </el-dialog>    
  </div>
</template>

<script>
import Clipboard from 'clipboard'
import CodeMirror from '@/components/CodeMirror'

export default {
    name: 'ConfigClipboard',
    components: {
        CodeMirror
    },
    data() {
        return {
            dialogTableVisible: false, // 外层对话框是否可见
            loading: false,        
            // 查询参数
            searchForm: {
                searchText: null,
                pageNum: 1,
                pageSize: 5
            },
            // 分页结果
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 5,
                list: []
            },
            dialogViewsVisible: false, // 预览配置内容对话框是否可见
            currentTemplate: {} // 当前点击预览/复制的模板
        }
    },
    methods: {
        /**
         * 查询配置模板
         */
        async getPage() {
            this.loading = true
            let response = await this.$API.tpl.getPage(this.searchForm)
            this.pageResult = response.data
            this.loading = false
        },
        /**
         * 页码改变事件
         */
        pageChange(val) {
            this.searchForm.pageNum = val
            this.getPage()
        },
        /**
         * 刷新查询
         */
        refresh() {
            this.searchForm.searchText = null
            this.getPage()
        },
        /**
         * 打开外层对话框
         */
        open() {
            this.dialogTableVisible = true
            this.getPage()
        },
        /**
         * 点击复制
         */
        copy(tplContent) {
            let clipboard = new Clipboard('.copy-btn', {
                text: function () {
                    return tplContent
                }
            })
            clipboard.on('success', e => {
                this.$message({message: '复制成功', showClose: true, type: 'success'})
                clipboard.destroy() // 释放内存
            })
            clipboard.on('error', e => {
                this.$message({message: '复制失败,', showClose: true, type: 'error'})
                clipboard.destroy()
            })
        },
        /**
         * 点击预览配置内容
         */
        view(tpl) {
            this.currentTemplate = tpl
            this.dialogViewsVisible = true
        },
        /**
         * 关闭预览窗口
         */
        closeViews() {
            this.dialogViewsVisible = false
            this.currentTemplate = null            
        }
    }
}
</script>


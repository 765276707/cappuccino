<template>
    <!--
        文件导入组件
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        width="600px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
        
        <el-upload
            ref="upload"
            :headers="headers"
            :action="action"
            :on-success="onSuccess"
            :on-error="onError"
            :on-remove="onRemove"
            :on-change="onChange"
            :accept="accept"
            multiple
            :limit="1"
            :auto-upload="false"
            >
            <el-button type="primary" icon="el-icon-folder-opened" plain>选择文件</el-button>
        </el-upload>

        <slot></slot>

        <!-- 导入结果 -->
        <div class="import-result" v-if="loading">
            <i class="el-icon-loading"></i> <span style="color: #909399;">正在导入中，请稍候......</span>
        </div>
        <div class="import-result" v-if="showResult">
            <p style="color: #67C23A;" v-if="result.success">{{ result.message }}</p>
            <p style="color: #F56C6C;" v-else>{{ result.message }}</p>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button type="success" @click="submitUpload" :disabled="submitDisabled"><i class="el-icon-upload el-icon--left"></i>上传并导入</el-button>
            <el-button @click="closeDialog">关 闭</el-button>
        </div>
    </el-dialog>
    
</template>

<script>
import { getHeaders } from '@/server/headers'

export default {
    name: 'ElFileImport',
    props: {
        visible: {
            type: Boolean,
            default: false 
        },
        title: {
            type: String,
            default: '导入数据'
        },
        // 上传地址
        action: {
            type: String,
            default: ''
        },
        //支持的文件拓展名
        accept: {
            type: String,
            default: '.json'
        }
    },
    data() {
        return  {
            submitDisabled: true, //禁用上传按钮
            showResult: false,    //是否展示导入结果
            loading: false,       //是否显示导入中
            countdown: 0, //倒计时
            result: {   //导入结果
                success: false,
                message: null,
                total: 0,
            } 
        }
    },
    computed: {
        // 上传头部参数
        headers() {
            return getHeaders()
        } 
    },
    beforeDestroy() {
        // 离开页面前检测是否销毁了定时器
        if (!this.timer) {
            clearInterval(this.timer)
        }
    },
    methods: {
        onSuccess(response, file, fileList) {
            // 结果赋值
            this.result.message = response.message
            this.result.success = response.success
            if (response.success) {
                this.result.total = response.data.total 
            }
            // 展示结果
            this.showResult = true
            this.loading = false 
        },

        onError(err, file, fileList) {
            let error = JSON.parse(err.toString().replace('Error:', ''))
            this.loading = false
            this.$message.error(error.errorMessage)
        },

        onChange(file, fileList) {
            // 判断是否禁用上传按钮
            if (file.status == 'success') {
                this.submitDisabled = true
            } else {
                this.submitDisabled = fileList.length > 0 ? false : true
            }     
        },

        onRemove(file, fileList) {
            // 判断是否禁用上传按钮
            this.submitDisabled = fileList.length > 0 ? false : true
            // 初始化数据
            this.result = Object.assign({}, initResult)
            this.showResult = false
        },

        submitUpload() {
            this.loading = true
            this.$refs.upload.submit()
        },

        closeDialog() {
            this.result = {}
            this.showResult = false
            this.loading = false
            this.submitDisabled = true
            this.$refs.upload.clearFiles()
            this.$emit('import-complete')
        }
    }
}
</script>

<style lang="scss" scoped>
.import-result {
    background-color: #F2F6FC;
    padding: 6px;
    margin-top: 10px;
    border-radius: 3px;
}
</style>
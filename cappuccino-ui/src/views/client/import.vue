<template>
    <div>
        <!--
            导入部分
        -->
        <file-import 
            title="导入客户端"
            :visible="visible"
            :action="action"
            :accept="accept"
            @import-complete="importComplete">
            <div style="margin-top: 10px;">
                <div>文件格式：<span style="color: #F56C6C;"> {{ accept }} </span> </div>
                <el-tag type="warning">提示</el-tag> 建议使用导出的JSON文件上传，导入数据的时会生成新编号的客户端，文件内容必须符合要求。
            </div>
        </file-import>

    </div>
    
</template>

<script>
import FileImport from '@/views/commons/file-import.vue'

export default {
    name: 'Import',
    components: {
        FileImport
    },
    props: {
        visible: {
            type: Boolean,
            default: false 
        }
    },
    data() {
        return  {
            accept: '.json' //支持的文件拓展名
        }
    },
    computed: {
        // 上传地址
        action() {
            return this.$API.client.imports()
        }
    },
    methods: {
        importComplete() {
            this.$emit('import-complete')
        },
    }
}
</script>

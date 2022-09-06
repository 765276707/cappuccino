<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="800px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :fullscreen="true"
        append-to-body>
        <el-form  ref="FORM" :model="form" :rules="rule" label-width="120px">
            <el-row>
                <!--隐藏id字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="id" v-show="false">
                        <el-input v-model="form.id"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <!--隐藏clientId字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="clientId" v-show="false">
                        <el-input v-model="form.clientId"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="配置描述" prop="description">
                        <el-input  type="textarea" :rows="3" v-model="form.description" placeholder="请输入配置描述" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="格式" prop="fileExtension">
                        <el-radio-group v-model="form.fileExtension" :disabled="fileExtensionDisabled">
                            <el-radio label="properties">properties</el-radio>
                            <el-radio label="yaml">yaml</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="配置内容" prop="content">
                        <code-mirror v-model="form.content" :mode="codeMirrorMode" :key="codeMirrorMode"></code-mirror>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center;">
            <el-button type="primary" @click="submit()">确认发布</el-button>
            <el-button @click="close()">取消发布</el-button>
        </div>
    </el-dialog>
</template>

<script>
import CodeMirror from '../commons/codemirror.vue'

export default {
    components: {
        CodeMirror
    },
    data() {
        return {
            title: '',
            visible: false,
            fileExtensionDisabled: false,
            form: {
            },
            rule: {
            },
            loading: false
        }
    },
    computed: {
        codeMirrorMode: function() {
            return this.form.fileExtension
        }
    },
    methods: {
        /**
         * 提交表单
         */
        async submit() {
            if (!this.valid('FORM')) return
            // 校验通过
            let response = null;
            if (!this.form.id) {
                // 新增
                response = await this.$API.config.save(this.form)
            } else {
                // 更新
                response = await this.$API.config.update(this.form)
            }
            this.$emit('success', this.form.id)
            this.$message.success('发布成功')
            this.close()
        },

        /**
         * 校验表单
         */
        valid(formRef) {
            let res = false;
            this.$refs[formRef].validate((valid) => {
                if (valid) {
                    res = valid
                }
            })
            return res;
        },

        /**
         * 打开表单
         */
        open(row) {
            this.visible = true
            if (row.id) {
                this.title = '编辑配置'   
                this.fileExtensionDisabled = true
                this.form = Object.assign({'clientId': row.clientId}, row)
            } else {
                this.title = '新增配置'
                this.fileExtensionDisabled = false
                this.form = Object.assign({'clientId': row.clientId, 'fileExtension': 'properties'}, row)
            }
        },

        /**
         * 关闭表单
         */
        close() {
            this.$refs['FORM'].resetFields()
            this.$refs['FORM'].clearValidate()
            this.visible = false
        }
    }
}
</script>

<style>

</style>
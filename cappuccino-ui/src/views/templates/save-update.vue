<template>

    <el-drawer
    :title="title" 
    :visible.sync="visible"
    v-if="visible"
    direction="rtl"
    size="1000px"
    :show-close="false"
    :wrapperClosable="false"
    :close-on-press-escape="false"
    append-to-body>
        <el-form ref="FORM" :model="form" :rules="rule" label-width="120px" style="padding-right: 50px;">
            <el-row>
                <!--隐藏id字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="id" v-show="false">
                        <el-input v-model="form.id"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="模板名称" prop="tplName">
                        <el-input :rows="3" v-model="form.tplName" placeholder="请输入配置名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="模板描述" prop="tplDesc">
                        <el-input  type="textarea" :rows="3" v-model="form.tplDesc" placeholder="请输入配置描述" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="格式" prop="tplFileExtension">
                        <el-radio-group v-model="form.tplFileExtension">
                            <el-radio label="properties">properties</el-radio>
                            <el-radio label="yaml">yaml</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="状态" prop="disabled">
                        <el-radio-group v-model="form.disabled">
                            <el-radio :label="false">启用</el-radio>
                            <el-radio :label="true">禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="配置内容" prop="tplContent">
                        <code-mirror v-model="form.tplContent" :mode="codeMirrorMode" :key="codeMirrorMode"></code-mirror>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div class="dialog-footer" style="text-align: center;">
            <el-button type="primary" @click="submit()">确 定</el-button>
            <el-button @click="close()">关 闭</el-button>
        </div>
    </el-drawer>

</template>

<script>
import CodeMirror from '@/components/CodeMirror'

export default {
    components: {
        CodeMirror
    },
    data() {
        return {
            title: '',
            visible: false,
            form: {},
            rule: {
                tplName: [
                    { required: true, message: '请输入模板名称', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ]
            },
            loading: false
        }
    },
    computed: {
        codeMirrorMode: function() {
            return this.form.tplFileExtension
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
                response = await this.$API.tpl.save(this.form)
            } else {
                // 更新
                response = await this.$API.tpl.update(this.form)
            }
            this.$emit('success', this.form.id)
            this.$message.success(response.message)
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
                this.title = '编辑模板'   
                this.form = Object.assign({}, row)
            } else {
                this.title = '新增模板'
                this.form = Object.assign({'tplFileExtension': 'properties', 'disabled': false}, row)
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

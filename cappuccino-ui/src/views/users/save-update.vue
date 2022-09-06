<template>
    <!--
        新增或编辑表单
    -->
    <el-dialog 
        :title="title" 
        :visible.sync="visible"
        v-if="visible"
        width="600px"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        append-to-body>
        <el-form  ref="FORM" :model="form" :rules="rule" label-width="120px">
            <!--隐藏id字段-->
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
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="form.username" placeholder="请输入用户名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="真实姓名" prop="realname">
                        <el-input v-model="form.realname" placeholder="请输入真实姓名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="联系电话" prop="phoneNumber">
                        <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="电子邮箱" prop="email">
                        <el-input v-model="form.email" placeholder="请输入电子邮箱" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="部门" prop="deptName">
                        <el-input v-model="form.deptName" placeholder="请输入部门名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="职位" prop="jobName">
                        <el-input v-model="form.jobName" placeholder="请输入职位名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="角色" prop="role">
                        <el-radio-group v-model="form.role">
                            <el-radio label="SA">超级管理员</el-radio>
                            <el-radio label="CA">普通管理员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>

            
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submit()">确 定</el-button>
            <el-button @click="close()">关 闭</el-button>
        </div>
    </el-dialog>
</template>

<script>

export default {
    data() {
        return {
            title: '',
            visible: false,
            form: {
                
            },
            rule: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            },
            loading: false
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
                response = await this.$API.user.save(this.form)
            } else {
                // 更新
                response = await this.$API.user.update(this.form)
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
                this.title = '编辑账号'    
            } else {
                this.title = '新增账号'
            }
            // 赋值
            this.form = Object.assign({}, row)
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
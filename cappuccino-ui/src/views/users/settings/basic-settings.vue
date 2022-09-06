<template>
    <el-row>
        <el-col style="width: 300px; padding-left: 20px;">
            <el-form ref="FORM" :model="form" :rules="rules" label-width="80px" label-position="top">
                <el-form-item label="真实姓名" prop="realname">
                    <el-input v-model="form.realname" placeholder="请输入真实姓名"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="phoneNumber">
                    <el-input v-model="form.phoneNumber" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入电子邮箱"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">更新基本资料</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
export default {
    name: 'BasicSettings',
    props: {
        value: {
            type: Object
        }
    },
    data() {
        return {
            rules: {
                phoneNumber: [{required: true, message: '请输入手机号码', trigger: 'blur'}]
            }
        }
    },
    computed: {
        form() {
            return this.value
        }
    },
    methods: {
        onSubmit() {
            this.$refs['FORM'].validate((valid) => {
                if (valid) {
                    this.$API.user.updateSelf(this.form).then(response => {
                        this.$message.success(response.message)
                        this.$emit('success')
                    })
                } else {
                    return false
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
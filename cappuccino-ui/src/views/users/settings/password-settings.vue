<template>
    <div>
        <el-row>
            <el-col style="padding-left: 20px;">
                <el-alert
                title="定期更换密码，更有效的保护您的账号安全"
                description="不建议您使用过于简单的密码或容易被猜到的密码，如多个重复或生日：666888、19901002"
                type="warning"
                show-icon>
                </el-alert>
            </el-col>
        </el-row>
        <el-row>
            <el-col style="width: 300px; padding-left: 20px;">
                <el-form ref="FORM" :model="form"  :rules="rules" label-width="80px" label-position="top">
                    <el-form-item label="原始密码" prop="oldPassword">
                        <el-input
                            :type="oldPasswordShow"
                            v-model="form.oldPassword"
                            prefix-icon="el-icon-s-goods"
                            placeholder="请输入原始密码"
                        ></el-input>
                        <span class="show-pwd" @click="showPassword(1)">
                            <svg-icon :icon-class="oldPasswordShow === 'password' ? 'eye' : 'eye-open'" />
                        </span>
                    </el-form-item>
                    <el-form-item label="新的密码" prop="newPassword">
                        <el-input
                            :type="newPasswordShow"
                            v-model="form.newPassword"
                            prefix-icon="el-icon-s-goods"
                            placeholder="请输入新的密码"
                        ></el-input>
                        <span class="show-pwd" @click="showPassword(2)">
                            <svg-icon :icon-class="newPasswordShow === 'password' ? 'eye' : 'eye-open'" />
                        </span>
                        <p style="font-size:12px; color:#909399;">提示：密码长度必须在6-20之间，至少包含一个字母和一个数字，不允许连续6个字母或数字字符，不允许空白字符</p>
                    </el-form-item>
                    <el-form-item label="重复密码" prop="repPassword">
                        <el-input
                            :type="repPasswordShow"
                            v-model="form.repPassword"
                            prefix-icon="el-icon-s-goods"
                            placeholder="请输入确认密码"
                        ></el-input>
                        <span class="show-pwd" @click="showPassword(3)">
                            <svg-icon :icon-class="repPasswordShow === 'password' ? 'eye' : 'eye-open'" />
                        </span>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">确认修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
    
</template>

<script>
export default {
    name: 'PasswordSettings',
    data() {
        return {
            form: {
                oldPassword: '',
                newPassword: '',
                repPassword: ''
            },
            rules: {
                oldPassword: [{required: true, message: '请输入原始密码', trigger: 'blur'}],
                newPassword: [{required: true, message: '请输入新的密码', trigger: 'blur'}],
                repPassword: [{required: true, message: '请输入确认密码', trigger: 'blur'}]
            },
            oldPasswordShow: 'password',
            newPasswordShow: 'password',
            repPasswordShow: 'password'
        }
    },
    methods: {
        onSubmit() {
            // 参数校验
            this.$refs['FORM'].validate((valid) => {
                if (valid) {

                    if (this.form.newPassword !== this.form.repPassword) {
                        return this.$message.error('新密码与确认密码不一致')
                    }
                    if (this.form.newPassword === this.form.oldPassword) {
                        return this.$message.error('新密码与原始密码重复')
                    }

                    this.$confirm('变更密码需要重新进行登录，是否继续?', '变更提示', {
                        confirmButtonText: '继续',
                        cancelButtonText: '取消',
                        type: 'warning',
                        center: true
                    }).then(() => {
                        
                        this.$API.user.updatePassword(this.form).then(response => {
                            this.$message.success(response.message)
                            setTimeout(() => {
                                this.logout()
                            }, 3000);
                        })

                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消变更'
                        })
                    })
                }
            })
            
        },

        async logout() {
            await this.$store.dispatch('user/logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },

        showPassword(index) {
            if (index === 1) {
                if (this.oldPasswordShow === 'password') {
                    this.oldPasswordShow = ''
                } else {
                    this.oldPasswordShow = 'password'
                }
            }

            if (index === 2) {
                if (this.newPasswordShow === 'password') {
                    this.newPasswordShow = ''
                } else {
                    this.newPasswordShow = 'password'
                }
            }

            if (index === 3) {
                if (this.repPasswordShow === 'password') {
                    this.repPasswordShow = ''
                } else {
                    this.repPasswordShow = 'password'
                }
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.show-pwd {
    position: absolute;
    right: 10px;
    /* top: 7px; */
    font-size: 16px;
    color: gray;
    cursor: pointer;
    user-select: none;
}
</style>
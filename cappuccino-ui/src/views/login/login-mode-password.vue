<template>
    <!-- 
        账号密码登录组件
     -->
    <el-form :model="loginForm" ref="loginForm" :rules="loginFormRules" size="medium">
        <el-form-item prop="username">
            <el-input
                v-model="loginForm.username"
                prefix-icon="el-icon-user-solid"
                placeholder="请输入账号"
            ></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input
                :type="isShowPassowrd"
                v-model="loginForm.password"
                prefix-icon="el-icon-s-goods"
                placeholder="请输入密码"
            ></el-input>
            <span class="show-pwd" @click="showPassword()">
                <svg-icon :icon-class="isShowPassowrd === 'password' ? 'eye' : 'eye-open'" />
            </span>
        </el-form-item>
        <el-form-item style="text-align:center;">
            <el-button :loading="loading" type="primary" @click="handleLogin('loginForm')">登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
// 验证码组件
import VerifyCode from "@/components/VerifyCode";

export default {
    name: "LoginPassword",
    components: {
        VerifyCode
    },
    data() {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            loginFormRules: {
                username: [{ required: true, message: "请输入账号", trigger: "blur" }],
                password: [{ required: true, message: "请输入密码", trigger: "blur" }]
            },
            loading: false,
            isShowPassowrd: 'password'
        }
    },
    created() {
        // 组件初始化就远程获取验证码
        // this.getVerifyCode()
    },
    methods: {

        /**
         * 是否明文显示密码
         */
        showPassword() {
            if (this.isShowPassowrd === 'password') {
                this.isShowPassowrd = ''
            } else {
                this.isShowPassowrd = 'password'
            }
        },

        /**
         * 提交登录
         * @param formName 表单名称
         */
        handleLogin(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {

                    // this.$router.push({ path: '/' })

                    this.loading = true
                    // 调用远程登录接口
                    this.$store.dispatch('user/login', this.loginForm).then(() => {
                        this.$router.push({ path: '/' })
                        this.loading = false
                    }).catch(error => {
                        console.error('login failure, ' + error)
                        this.loading = false
                    })
                } else {
                    console.error("error submit!");
                    return false;
                }
            });
        },
    }
}
</script>

<style>
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
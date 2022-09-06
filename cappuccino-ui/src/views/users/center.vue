<template>
    <div class="page-container">
        <el-row>
            <!-- 个人详情展示 -->
            <el-col :xs="24" :span="7" >
                <div class="left">
                    <el-card style="background-color: #F2F6FC;">
                        <div v-if="!loading">
                            <!-- 头像 -->
                            <el-row type="flex" justify="center">
                                <el-avatar :size="120">
                                    <i class="el-icon-s-custom" style="font-size: 100px;"></i>
                                </el-avatar>
                            </el-row>
                            
                            <!-- 用户名、签名 -->
                            <el-row type="flex" justify="center" style="padding-top: 10px; font-size: 20px;">
                                <b>{{ userInfo.username }}</b>
                            </el-row>
                            <el-row type="flex" justify="center" style="padding-top: 10px; font-size: 20px;">
                                <el-tag type="success" size="medium">{{ userInfo.role === 'SA' ? '超级管理员' : '普通管理员' }}</el-tag>
                            </el-row>
                            
                            <!-- 职位、部门、地址... -->
                            <el-row type="flex" justify="left" class="user-info-item">
                                <i class="el-icon-user"></i>{{ userInfo.realname }}
                            </el-row>
                            <el-row type="flex" justify="left" class="user-info-item">
                                <i class="el-icon-collection-tag"></i> {{ userInfo.deptName }}
                            </el-row>
                            <el-row type="flex" justify="left" class="user-info-item">
                                <i class="el-icon-postcard"></i>{{ userInfo.jobName }}
                            </el-row>
                            <el-row type="flex" justify="left" class="user-info-item">
                                <i class="el-icon-phone-outline"></i>{{ userInfo.phoneNumber }}
                            </el-row>
                            <el-row type="flex" justify="left" class="user-info-item">    
                                <i class="el-icon-date"></i>{{ userInfo.email }}
                            </el-row>
                        </div>
                        <el-skeleton :loading="loading" :rows="9" animated></el-skeleton>
                    </el-card>
                </div>
            </el-col>

            <!-- 个人关联信息 -->
            <el-col :xs="24" :span="17" >
                <div class="right">
                    <el-card>
                        <el-tabs :tab-position="'left'" v-if="!loading">
                            <el-tab-pane label="基本设置">
                                <p style="padding-left: 20px;">基本设置</p>
                                <basic-settings :value="loginUser" @success="getLoginUser"></basic-settings>
                            </el-tab-pane>
                            <el-tab-pane label="修改密码">
                                <p style="padding-left: 20px;">修改密码</p>
                                <password-settings></password-settings>
                            </el-tab-pane>
                        </el-tabs>
                        <el-skeleton :loading="loading" :rows="10" animated></el-skeleton>
                    </el-card>
                </div>           
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

import BasicSettings from './settings/basic-settings'
import PasswordSettings from './settings/password-settings.vue'

export default {
    name: 'UserCenter',
    components: {
        BasicSettings,
        PasswordSettings
    },
    data() {
        return {
            loading: false,
            loginUser: {},
            userInfo: {}
        }
    },
    computed: {
        ...mapGetters([
            'name'
        ])
    },
    created() {
        this.getLoginUser()
    },
    methods: {

        /**
         * 获取当前登录用户
         */
        async getLoginUser() {
            this.loading = true
            let response = await this.$API.auth.getLoginSelf()   
            this.loginUser = response.data
            this.userInfo = response.data
            this.loading = false
        }

    }
}
</script>


<style lang="scss" scoped>
.left {
    margin: 10px;
}

.right {
    margin: 10px;
}

.el-divider--horizontal{
     margin: 8px 0;
     background: 0 0;
     border-top: 1px dashed #e8eaec;
 }

.user-info-item {
    padding-top: 20px;
    padding-left:30px;
    font-size: 14px;
}


</style>
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

            <el-row>
                <el-col :span="24">
                    <el-form-item label="克隆的客户端" prop="clientName">
                        <div v-for="item in clientDetails" :key="item.clientName">
                            <el-tag type="info" size="mini"> {{ item.clientName }} </el-tag> 
                            <!-- <span v-if="item.cloneable!=null">{{ item.cloneable?'检测通过':'已存在' }}</span> -->
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="目标环境" prop="envId">
                        <env-select v-model="form.envId" :excludeId="excludeEnvId" 
                            :placeholder="'请选择要拷贝的目标环境'" style="width: 100%"></env-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="目标分组" prop="groupId">
                        <group-select v-model="form.groupId"
                            :placeholder="'请选择要拷贝的目标分组'" style="width: 100%"></group-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="同步配置" prop="withConfig">
                        <el-checkbox v-model="form.withConfig"></el-checkbox>
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
import EnvSelect from '../commons/env-select.vue'
import GroupSelect from '../commons/group-select.vue'

export default {
    components: {
        EnvSelect,
        GroupSelect
    },
    data() {
        return {
            title: '',
            visible: false,
            excludeEnvId: null,
            clientDetails: [],
            form: {
                clientIds: [],
                envId: null,
                groupId: null,
                withConfig: true
            },
            rule: {
                // envId: [{ required: true, message: '请选择要拷贝的环境', trigger: 'blur' }],
            },
            loading: false,
        }
    },
    watch() {
        
    },
    methods: {
        /**
         * 提交表单
         */
        async submit() {
            if (!this.valid('FORM')) return
            // 校验通过
            let response = await this.$API.client.clone(this.form)
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

        // envIdChange(val) {
        //     this.$API.client.checkCloneable(this.clientDetails).then(response => {
        //         this.clientDetails = response.data
        //     })
        // },


        /**
         * 打开表单
         */
        open(rows) {
            this.visible = true
            this.title = '克隆客户端'
            // 赋值
            this.form.clientIds = rows.map((row, index) => {
                return row.id
            })
            this.clientDetails = rows.map((row, index) => {
                return {'clientId': row.id, 'clientName': row.clientName, 'envId': row.envId, 'cloneable': null}
            })
            this.excludeEnvId = rows[0].envId   
        },

        /**
         * 关闭表单
         */
        close() {
            this.clientNames = []
            this.form.clientIds = []
            this.form.envId = null
            this.form.cloneConfig = true

            this.$refs['FORM'].resetFields()
            this.$refs['FORM'].clearValidate()
            this.visible = false
        }
    }
}
</script>

<style>

</style>
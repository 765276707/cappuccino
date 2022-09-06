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
                    <el-form-item label="客户端名称" prop="clientName">
                        <el-input v-model="form.clientName" placeholder="请输入客户端名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="所属环境" prop="envId">
                        <env-select v-model="form.envId" :placeholder="'请选择环境'" style="width: 100%"></env-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="所属分组" prop="groupId">
                        <group-select  v-model="form.groupId" :placeholder="'请选择分组'" style="width: 100%"></group-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="负责人" prop="charger">
                        <el-input v-model="form.charger" placeholder="请输入负责人姓名" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="负责人电话" prop="chargerPhoneNumber">
                        <el-input v-model="form.chargerPhoneNumber" placeholder="请输入负责人电话" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="客户端描述" prop="clientDesc">
                        <el-input  type="textarea" :rows="3" v-model="form.clientDesc" placeholder="请输入客户端描述" style="width: 100%"/>
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
        EnvSelect, GroupSelect
    },
    data() {
        return {
            title: '',
            visible: false,
            form: {
                
            },
            rule: {
                clientName: [{ required: true, message: '请输入客户端名称', trigger: 'blur' }],
                envId: [{ required: true, message: '请选择一个环境', trigger: 'change' }],
                groupId: [{ required: true, message: '请选择一个分组', trigger: 'change' }]
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
                response = await this.$API.client.save(this.form)
            } else {
                // 更新
                response = await this.$API.client.update(this.form)
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
                this.title = '编辑客户端'    
            } else {
                this.title = '新增客户端'
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
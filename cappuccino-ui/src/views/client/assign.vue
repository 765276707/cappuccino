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
        <el-form  ref="FORM"  label-width="120px">
            <!--隐藏id字段-->
            <!-- <el-row>
                <el-col :span="24" v-show="false">
                    <el-form-item prop="clientId" v-show="false">
                        <el-input v-model="clientId"/>
                    </el-form-item>
                </el-col>
            </el-row> -->
            <el-row>
                <el-col :span="24">
                    <el-form-item label="允许读写" prop="userIds">
                        <el-checkbox-group v-model="userIds">
                            <el-checkbox v-for="item in options" :key="item.value" :label="item.value" :disabled="item.disabled">{{item.label}}</el-checkbox>
                        </el-checkbox-group>
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
    components: {
    },
    data() {
        return {
            title: '',
            visible: false,
            clientId: null,
            userIds: [],
            options: [],
            loading: false
        }
    },
    beforeDestroy() {
    },
    methods: {
        async getRelations() {
            let response = await this.$API.relation.getRelations(this.clientId)
            this.userIds = response.data.userIds
            this.options = response.data.options
        },

        /**
         * 提交表单
         */
        async submit() {
            // if (!this.valid('FORM')) return
            // 校验通过
            let data = {'resourceId': this.clientId, 'resourceType': 'c', 'userIds': this.userIds}
            let response = await this.$API.relation.assign(data)
            // this.$emit('success', this.form.id)
            // this.getRelations()
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
            this.title = '授权'
            // 赋值
            this.clientId = row.id
            this.getRelations()
            // this.form = Object.assign({}, row)
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
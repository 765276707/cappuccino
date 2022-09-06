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
                <!--隐藏id字段-->
                <el-col :span="24" v-show="false">
                    <el-form-item prop="id" v-show="false">
                        <el-input v-model="form.id"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="分组名称" prop="groupName">
                        <el-input :rows="3" v-model="form.groupName" placeholder="请输入分组名称" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="分组描述" prop="groupDesc">
                        <el-input  type="textarea" :rows="3" v-model="form.groupDesc" placeholder="请输入分组描述" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="排序号" prop="orderNum">
                        <el-input-number v-model="form.orderNum" :min="1" label="描述文字"></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center;">
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
                response = await this.$API.group.save(this.form)
            } else {
                // 更新
                response = await this.$API.group.update(this.form)
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
                this.title = '编辑分组'   
                this.form = Object.assign({}, row)
            } else {
                this.title = '新增分组'
                this.form = Object.assign({}, row)
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
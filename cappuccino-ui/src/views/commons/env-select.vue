<template>
    <el-select v-model="selectValue" 
                :filterable="filterable" 
                :clearable="clearable" 
                :placeholder="placeholder"
                :multiple="multiple"
                v-on="$listeners">
        <!--选项属性-->        
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
    </el-select>
</template>

<script>
export default {
    name: 'EnvSelect',
    props: {
        value: {
            type: Number
        },
        type: {
            type: String
        },
        excludeId: {
            type: Number,
            default: null
        },
        placeholder: {
            type: String,
            default: ''
        },
        filterable: {
            type: Boolean,
            default: true
        },
        clearable: {
            type: Boolean,
            default: true 
        },
        multiple: {
            type: Boolean,
            default: false 
        }
    },
    data() {
        return {
            options: []
        }
    },
    computed: {
        selectValue: {
            get: function() {
                return this.value
            },
            set: function(val) {
                // 通知父组件更新绑定值
                this.$emit('input', val)
            }
        },
    },
    created() {
        this.getEnvironmentList();
    },
    methods: {
        /**
         * @title 根据类型加载环境列表
         */
        async getEnvironmentList() {
            let response = await this.$API.environment.getList(this.excludeId);
            this.options = response.data
        }
    }
}
</script>

<style>

</style>
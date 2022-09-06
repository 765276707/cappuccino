<template>
    <el-select v-model="selectValue" 
                :filterable="filterable" 
                :clearable="clearable" 
                :placeholder="placeholder"
                :multiple="multiple">
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
    name: 'GroupSelect',
    props: {
        value: {
            type: String
        },
        type: {
            type: String
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
        this.getGroupList();
    },
    methods: {
        /**
         * @title 根据类型加载分组列表
         */
        async getGroupList() {
            let response = await this.$API.group.getList();
            this.options = response.data
        }
    }
}
</script>

<style>

</style>
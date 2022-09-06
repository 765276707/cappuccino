<template>
    <div>
        <el-input
            placeholder="搜索客户端"
            style="width: 250px;"
            clearable
            v-model="searchText">
            <el-button slot="append" icon="el-icon-search" @click.native="doSearch"></el-button>
        </el-input>
    </div>
</template>

<script>
export default {
    data() {
        return {
            searchText: ''
        }
    },
    methods: {
        doSearch() {
            this.$router.push({ path: '/search', query: { searchText: this.searchText }})
        }
    }
}
</script>

<style>

</style>
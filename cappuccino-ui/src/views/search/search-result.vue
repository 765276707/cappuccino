<template>
    <el-container>
        <el-header height="90px" style="padding: 10px;">
            <!-- 工具/搜索栏 -->
            <div style="padding: 5px; background: #F2F6FC; border-radius: 5px;">
                <div class="topbar">
                    查询关于&nbsp;&nbsp;
                    <div style="color: #F56C6C; font-size: 22px;">
                        {{ searchText }}
                    </div>
                    &nbsp;&nbsp;的客户端有
                    <div style="color: #67C23A;">
                    {{pageResult.total}}
                    </div>
                    个
                </div>
            </div>       
        </el-header>

        <el-main style="padding-left: 10px; padding-right: 10px;">
            <el-row :gutter="16" v-if="pageResult.total">
                <el-col v-for="row in pageResult.list" :key="row.id" 
                    :xs="24"
                    :sm="12"
                    :md="8"
                    :lg="6"
                    :xl="4"
                >
                    <client-card :row="row" @manage-config="manageConfig"></client-card>
                </el-col>
            </el-row>
            <el-empty v-else :description="'没有搜索到相关客户端'" :image-size="100"></el-empty>

            <pagination :total="pageResult.total" @page-change="pageChange" style="text-align:center;"></pagination>
            
        </el-main>

        <save-update ref="SaveUpdate" @success="getPage"></save-update>

    </el-container>
</template>

<script>
import ClientCard from '@/components/Card/client-card.vue'

export default {
    components: {
        ClientCard
    },
    data() {
        return {
            searchForm: {
                searchText: ''
            },
            pageResult: { 
                total: 0,
                pageNum: 1,
                pageSize: 10,
                list: []
            },
            loading: false
        }
    },
    created() {
        this.getPage()
    },
    computed: {
        searchText() {
            return this.$route.query.searchText
        }
    },
    watch: {
        searchText(newVal, oldVal) {
            this.getPage()
        }
    },
    methods: {
        /**
         * @title 获取列表
         */
        async getPage() {
            this.loading = true
            try {
                this.searchForm.searchText = this.searchText
                let response = await this.$API.client.getPage(this.searchForm)   
                this.pageResult = response.data
            } catch(err) {
                this.loading = false
            }
            this.loading = false
        },

        /**
         * @title 更改分页
         */
        pageChange(pageNum, pageSize) {
            this.searchForm.pageNum = pageNum
            this.searchForm.pageSize = pageSize
            this.getPage()
        },

        manageConfig(row) {
            this.$router.push({ path: '/config', query: { clientId: row.id }})
        }
    }
}
</script>

<style lang="scss" scoped>
.el-main {
    width: 100%;
    height: 100%;
    padding: 0;
}
.topbar {
    height: 60px; 
    display: flex;
    justify-content: center;
    margin-bottom: 0px;
    line-height: 60px;

    .title {
        text-align: center;
        color: red;
    }

}
</style>
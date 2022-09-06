<template>
    <div>
        <!--
            监听实例
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

            <div v-if="instances.length">



                <el-card v-for="item in instances" :key="item" body-style="padding: 10px; background-color: #FFEFD5; color: #409EFF;">
                    <i class="el-icon-monitor"></i> {{ item }} <el-tag type="success" size="mini">正在监听</el-tag>
                </el-card>

            </div>
            
            <el-empty v-else :description="'暂无监听的实例'" :image-size="100"></el-empty>

            <div slot="footer" class="dialog-footer" style="text-align: center;">
                <el-button @click="close()">关 闭</el-button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
export default {
    data() {
        return {
            title: '',
            visible: false,
            loading: false,
            clientId: null,
            instanceType: null,
            instances: [],
            timer: null
        }
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },
    methods: {
        
        /**
         * @title 获取列表
         */
        async getList() {
            this.loading = true
            let response;
            if (this.instanceType === 'GRAYSCALE') {
                response = await this.$API.monitor.getGrayscaleInstances(this.clientId)   
            } else {
                response = await this.$API.monitor.getInstances(this.clientId)  
            }    
            this.instances = response.data
            this.loading = false
        },


        /**
         * 打开表单
         */
        open(clientId, instanceType) {
            this.visible = true
            this.title = '监听实例'   
            this.clientId = clientId
            this.instanceType = instanceType
            this.getList()
            this.timer = setInterval(this.getList, 5000);
        },

        /**
         * 关闭表单
         */
        close() {
            this.visible = false
            clearInterval(this.timer);
        }
        
    }
}
</script>

<style>

</style>
<template>
  <div class="dashboard-container">
    <!-- <el-row :gutter="6">
      <el-col :span="6">
        <el-card>
          <p>当前环境数：{{ staticsInfo.envCount }}</p>
          <p>当前分组数：{{ staticsInfo.groupCount }}</p>
          <p>当前客户端数：{{ staticsInfo.clientCount }}</p>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>最近浏览的客户端</span>
          </div>
        </el-card>
      </el-col>
    </el-row> -->
    
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  components: {
  },
  data() {
    return {
      timer: null,
      staticsInfo: null
    }
  },
  created() {
    // this.getClientInfo()
    // this.timer = setInterval(() => {
    //   this.getClientInfo()
    // }, 10000)
  },
  destroyed() {
    // 离开页面时，销毁定时器
    clearInterval(this.timer)
  },
  methods: {
    /**
     * 查询客户端详情
     */
    async getClientInfo() {
      let response = await this.$API.client.getInfo()   
      this.staticsInfo = response.data
    },

  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }

}
</style>

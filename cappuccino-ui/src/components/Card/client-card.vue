<template>
    <div class="list-card-item">
        <div class="list-card-item_detail">
        <el-row type="flex" justify="space-between">
            <div class="list-card-item_detail--logo">
                <!-- <i class="el-icon-s-order"></i> -->
                {{ row.clientName!==null?row.clientName.substr(0,1):'' }}
            </div>
            <div class="list-card-item_detail--operation">
                <el-button type="primary" icon="el-icon-edit" round  @click.native="manageConfig(row)">管理配置</el-button>
            </div>
        </el-row>
        <p class="list-card-item_detail--name">{{ row.id }} . {{ row.clientName }}</p>
        <p class="list-card-item_detail--desc">{{ row.clientDesc }}</p>
        <p class="list-card-item_detail--info">
            环境：<el-tag type="success"  class="mx-1 list-card-item_detail--operation--tag">{{ row.envName }}</el-tag>        
        </p>
        <p class="list-card-item_detail--info">
            分组：<el-tag type="warning"  effect="dark"  class="mx-1 list-card-item_detail--operation--tag">{{ row.groupName }}</el-tag>        
        </p>
        <p class="list-card-item_detail--info">创建者：{{ row.createUser }}</p>
        </div>
    </div>
</template>

<script>
export default {
    name: 'CilentCard',
    props: {
        row: {
            type: Object,
            require: true
        }
    },
    methods: {
        manageConfig(row) {
            this.$emit('manage-config', row)
        }
    }
}
</script>

<style lang="scss" scoped>
$text-color-disabled: rgba(0, 0, 0, 0.26);

.list-card-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
  border-radius: 3px;
  overflow: hidden;
  cursor: pointer;
  color: rgba(0, 0, 0, 0.6);

  &_detail {
    flex: 1;
    background: #fff;
    padding: 24px 32px;
    min-height: 140px;

    &--logo {
      width: 56px;
      height: 56px;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      background: #e0ebff;
      font-size: 32px;
      color: #0052d9;

      &__disabled {
        color: #a1c4ff;
      }
    }

    &--operation {
      display: flex;
      height: 100%;

      &--tag {
        border: 0;
      }
    }

    .icon-more {
      font-size: 24px;
      color: rgba(36, 36, 36, 1);
    }

    &--name {
      margin: 24px 0 8px 0;
      font-size: 16px;
      font-weight: 400;
      color: rgba(0, 0, 0, 0.9);
    }

    &--desc {
      font-size: 12px;
      line-height: 20px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    //   margin-bottom: 24px;
    //   height: 40px;
    }

    &--info {
      font-size: 12px;
      line-height: 12px;
      overflow: hidden;
    }
  }

  &__disabled {
    color: $text-color-disabled;

    .icon-more {
      color: $text-color-disabled;
    }

    .list-card-item_detail--name {
      color: $text-color-disabled;
    }

    .list-card-item_detail--operation--tag {
      color: #bababa;
    }
  }
}
</style>
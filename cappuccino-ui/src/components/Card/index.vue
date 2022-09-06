<template>
    <div class="list-card-item">
        <div class="list-card-item_detail">
        <el-row type="flex" justify="space-between">
            <div class="list-card-item_detail--logo">
                <!-- <i class="el-icon-platform-eleme"></i> -->
                1000
            </div>
            <div class="list-card-item_detail--operation">
                <el-tag
                    :color="!row.disabled ? '#00a870' : '#eee'"
                    effect="dark"
                    class="mx-1 list-card-item_detail--operation--tag"
                >
                    {{ !row.disabled ? "已启用" : "已停用" }}
                </el-tag>
                <el-dropdown
                    trigger="click"
                    :disabled="row.disabled"
                    max-height="2"  
                    size="medium"             
                >
                    <i class="el-icon-more-outline" style="margin-left: 10px; font-size: 22px;"></i>
                    <template #dropdown>
                    <el-dropdown-menu :disabled="row.disabled" >
                        <el-dropdown-item @click.native="handleClickUpdate(row)">
                        编辑
                        </el-dropdown-item>
                        <el-dropdown-item @click.native="handleClickDelete(row)">
                        删除
                        </el-dropdown-item>
                    </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </el-row>
        <p class="list-card-item_detail--name">{{ row.tplName }}</p>
        <p class="list-card-item_detail--desc">{{ row.tplDesc }}</p>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Card',
    props: {
        row: {
            type: Object,
            require: true
        }
    },
    methods: {
        handleClickUpdate(row) {
            this.$emit('row-update', row)
        },
        handleClickDelete(row) {
            this.$emit('row-delete', row)
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
      margin-bottom: 24px;
      height: 40px;
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
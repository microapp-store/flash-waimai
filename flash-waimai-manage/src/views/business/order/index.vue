<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="24">
        <el-col :span="4">
          <el-input v-model="listQuery.restaurant_id" placeholder="餐厅id"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input v-model="listQuery.id" placeholder="订单ID"></el-input>
        </el-col>

        <el-col :span="8">
          <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>

    </div>

    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
              @expand-change="expandRow"
              :expand-row-keys="expandRowData"
              :row-key="getRowKeys">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="application-table-expand">
            <el-form-item label="用户名" >
              <span>{{ props.row.user_name }}</span>
            </el-form-item>
            <el-form-item label="店铺名称">
              <span>{{ props.row.restaurant_name }}</span>
            </el-form-item>
            <el-form-item label="收货地址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
            <el-form-item label="店铺 ID">
              <span>{{ props.row.restaurant_id }}</span>
            </el-form-item>
            <el-form-item label="店铺地址">
              <span>{{ props.row.restaurant_address }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
        label="订单id"
        prop="id">
      </el-table-column>
      <el-table-column
        label="总价格"
        prop="total_amount">
      </el-table-column>
      <el-table-column
        label="状态"
        prop="status_bar.title">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-dropdown  size="small"   split-button type="primary">

            操作
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item  @click.native="handleViewOrder(scope.row)">查看详情</el-dropdown-item>
              <el-dropdown-item  v-permission="['/business/food/delete']" v-if="scope.row.status_bar.title == '等待支付' || scope.row.status_bar.title == '已支付'"
                                 @click.native="handleUpdateOrderStatus(scope.row,2)">确认下单</el-dropdown-item>
              <el-dropdown-item  v-permission="['/business/food/delete']" v-if="scope.row.status_bar.title == '制作中'" @click.native="handleUpdateOrderStatus(scope.row,3)">确认派送</el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100,500]"
      :page-size="listQuery.limit"
      :total="total"
      @size-change="changeSize"
      @current-change="fetchPage"
      @prev-click="fetchPrev"
      @next-click="fetchNext">
    </el-pagination>

  </div>
</template>

<script src="./order.js"></script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

<template>
  <div class="app-container">
    <div class="block">
      <el-row>
        <!--<el-col :span="24">-->
          <!--<el-button type="success" size="mini" icon="el-icon-edit" @click.native="openSendOutForm"-->
                     <!--v-show="form.statusName =='待发货'">发货-->
          <!--</el-button>-->
          <!--<el-button type="default" size="mini" @click="printOrder">打 印</el-button>-->
        <!--</el-col>-->
      </el-row>
    </div>

    <el-form ref="print"   label-width="150px" >
      <el-row>
        <h3>基本信息</h3>
        <el-col :span="12">
          <el-form-item label="订单Id">
            <span v-if="form.order"> {{ form.order.id}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单用户">
              {{ form.user.username}}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总金额">
            <span> {{ form.order.total_amount}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <span> <strong>{{ form.order.status_bar.title }} </strong></span>
          </el-form-item>
        </el-col>

        <h3>收货信息</h3>
        <el-col :span="12">
          <el-form-item label="收货人">
            <span>{{ form.address.name}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话">
            <span>{{ form.address.phone}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收货地址">
            <span>{{ form.address.address}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <h3>订单明细</h3>
        </el-col>
        <el-table :data="form.orderItems" border fit  >
          <el-table-column label="商品">
            <template slot-scope="scope">
              <!--<router-link :to="{path:'goodsEdit?id='+scope.row.goods.id}">-->
                {{scope.row.foodName}}
              <!--</router-link>-->
            </template>
          </el-table-column>
          <el-table-column label="图片">
            <template slot-scope="scope">
            <img :src="apiUrl+ '/file/getImgStream?fileName=' + scope.row.image_path" style="width:50px;">
            </template>
          </el-table-column>
          <el-table-column label="价格">
            <template slot-scope="scope">
              {{formatPrice(scope.row.price)}}
            </template>
          </el-table-column>
          <el-table-column label="数量">
            <template slot-scope="scope">
              {{scope.row.quantity}}
            </template>
          </el-table-column>
          <el-table-column label="打包费">
            <template slot-scope="scope">
              {{scope.row.packingFee}}
            </template>
          </el-table-column>
          <el-table-column label="合计">
            <template slot-scope="scope">
              {{ formatPrice(scope.row.totalPrice)}}
            </template>
          </el-table-column>


        </el-table>


      </el-row>

    </el-form>

  </div>
</template>

<script src="./orderDetail.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>


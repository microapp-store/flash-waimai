<template>
  <div class="app-container">
    <div class="block">
      <el-button
        size="mini"
        type="success"
        v-permission="['/business/food/edit']"
        @click="addFood(selectTable)">添加食品
      </el-button>
      <el-button
        size="mini"
        type="info"
        @click="viewFood(selectTable)">查看食品
      </el-button>

      <el-button
        size="mini"
        type="primary"
        @click="updateShop">提交修改
      </el-button>


    </div>
    <br>

    <el-form :model="selectTable">
      <el-form-item label="审核状态" label-width="120px">
        <el-input v-model="selectTable.stateStr"   :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="审核结果" label-width="120px">
        <el-input v-model="selectTable.auditRemark"  :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="店铺名称" label-width="120px">
        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="未结算交易额(元)" label-width="120px">
        <el-input v-model="selectTable.unliquidatedAmount"  :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="已结算交易额（元)" label-width="120px">
        <el-input v-model="selectTable.totalAmount"  :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="平台收取费率(%)" label-width="120px">
        <el-input v-model="selectTable.platform_rate"  type="number"></el-input>
      </el-form-item>


      <el-form-item label="详细地址" label-width="120px">
        <el-autocomplete
          v-model="address.address"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入地址"
          style="width: 100%;"
          @select="addressSelect"
        ></el-autocomplete>
        <span>当前城市：{{city.name}}</span>
      </el-form-item>
      <el-form-item label="店铺介绍" label-width="120px">
        <el-input v-model="selectTable.description"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" label-width="120px">
        <el-input v-model="selectTable.phone"></el-input>
      </el-form-item>
      <el-form-item label="店铺分类" label-width="120px">
        <el-cascader
          :options="categoryOptions"
          v-model="selectedCategory"
          change-on-select
        ></el-cascader>
      </el-form-item>
      <el-form-item label="商铺图片" label-width="100px">
        <el-upload
          class="avatar-uploader"
          :action="fileMgrUrl"
          :headers="uploadHeaders"
          :show-file-list="false"
          :on-success="handleServiceAvatarScucess"
          :before-upload="beforeAvatarUpload">
          <img v-if="selectTable.image_path" :src="selectTable.image" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>


  </div>
</template>

<script src="./sdetail.js"></script>


<style lang="less">
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .table_container {
    padding: 20px;
  }

  .Pagination {
    display: flex;
    justify-content: flex-start;
    margin-top: 8px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }

  .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }
</style>

<template>
  <div class="app-container">
    <div class="block">
      <el-row>
        <el-col :span="12" :offset="4">
          <el-form :model="formData" :rules="rules" ref="formData" label-width="120px" class="demo-formData">
            <el-form-item label="店铺名称" prop="name">
              <el-input v-model="formData.name"></el-input>
            </el-form-item>
            <el-form-item label="详细地址" prop="address">
              <el-autocomplete
                v-model="formData.address"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入地址"
                style="width: 100%;"
                @select="addressSelect"
              ></el-autocomplete>
              <span>当前城市：{{city.name}}</span>
            </el-form-item>

            <el-form-item label="联系电话" prop="phone">
              <el-input v-model.number="formData.phone" maxLength="11"></el-input>
            </el-form-item>
            <el-form-item label="店铺简介" prop="description">
              <el-input v-model="formData.description"></el-input>
            </el-form-item>
            <el-form-item label="店铺标语" prop="promotion_info">
              <el-input v-model="formData.promotion_info"></el-input>
            </el-form-item>
            <el-form-item label="店铺分类">
              <el-cascader
                :options="categoryOptions"
                v-model="selectedCategory"
                change-on-select
              ></el-cascader>
            </el-form-item>
            <el-form-item label="平台收取费率(%)" prop="description">
              <el-input type="number" v-model="formData.platform_rate"></el-input>
            </el-form-item>
            <el-form-item label="店铺特点" style="white-space: nowrap;">
              <span>品牌保证</span>
              <el-switch on-text="" off-text="" v-model="formData.is_premium"></el-switch>
              <span>蜂鸟专送</span>
              <el-switch on-text="" off-text="" v-model="formData.deliveryMode"></el-switch>
              <span>新开店铺</span>
              <el-switch on-text="" off-text="" v-model="formData.news"></el-switch>
            </el-form-item>
            <el-form-item style="white-space: nowrap;">
              <span>外卖保</span>
              <el-switch on-text="" off-text="" v-model="formData.bao"></el-switch>
              <span>准时达</span>
              <el-switch on-text="" off-text="" v-model="formData.zhun"></el-switch>
              <span>开发票</span>
              <el-switch on-text="" off-text="" v-model="formData.piao"></el-switch>
            </el-form-item>
            <el-form-item label="配送费" prop="float_delivery_fee">
              <el-input-number v-model="formData.float_delivery_fee" :min="0" :max="20"></el-input-number>
            </el-form-item>
            <el-form-item label="起送价" prop="float_minimum_order_amount">
              <el-input-number v-model="formData.float_minimum_order_amount" :min="0" :max="100"></el-input-number>
            </el-form-item>
            <el-form-item label="营业时间" style="white-space: nowrap;">
              <el-time-select
                placeholder="起始时间"
                v-model="formData.startTime"
                :picker-options="{
							start: '05:30',
							step: '00:15',
							end: '23:30'
							}">
              </el-time-select>
              <el-time-select
                placeholder="结束时间"
                v-model="formData.endTime"
                :picker-options="{
							start: '05:30',
							step: '00:15',
							end: '23:30',
							minTime: formData.startTime
							}">
              </el-time-select>
            </el-form-item>

            <el-form-item label="上传店铺头像">
              <el-upload
                class="avatar-uploader"
                :action="fileMgrUrl"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleShopAvatarScucess"
                :before-upload="beforeAvatarUpload">
                <img v-if="formData.image_path" :src="formData.imagePath" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="上传营业执照">
              <el-upload
                class="avatar-uploader"
                :action="fileMgrUrl"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleBusinessAvatarScucess"
                :before-upload="beforeAvatarUpload">
                <img v-if="formData.business_license_image" :src="formData.businessLicenseImage"
                     class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="上传餐饮服务许可证">
              <el-upload
                class="avatar-uploader"
                :action="fileMgrUrl"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleServiceAvatarScucess"
                :before-upload="beforeAvatarUpload">
                <img v-if="formData.catering_service_license_image"
                     :src="formData.cateringServiceLicenseImage" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="优惠活动">
              <el-select v-model="activityValue" @change="selectActivity" :placeholder="activityValue">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-table
              :data="activities"
              style="min-width: 600px;margin-bottom: 20px;"
              align="cneter"
              :row-class-name="tableRowClassName">
              <el-table-column
                prop="icon_name"
                label="活动标题"
                align="cneter"
                width="120">
              </el-table-column>
              <el-table-column
                prop="name"
                label="活动名称"
                align="cneter"
                width="120">
              </el-table-column>
              <el-table-column
                prop="description"
                align="cneter"
                label="活动详情">
              </el-table-column>
              <el-table-column
                label="操作"
                width="120">
                <template slot-scope="scope">
                  <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-form-item class="button_submit">
              <el-button type="primary" @click="submitForm('formData')">立即创建</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script src="./add.js"></script>

<style lang="less">
  .button_submit {
    text-align: center;
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

  .el-table .info-row {
    background: #c9e5f5;
  }

  .el-table .positive-row {
    background: #e2f0e4;
  }
</style>

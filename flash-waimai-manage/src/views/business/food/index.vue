<template>

    <div class="table_container">
      <div class="fillcontain">

        <el-row :gutter="20">
          <el-col :span="6">
            <el-input v-model="listQuery.name" placeholder="食品名称"></el-input>
          </el-col>
          <el-col :span="6">
            <el-select v-model="listQuery.state" placeholder="请选择审核状态">
              <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
            <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
          </el-col>
        </el-row>
        <br>
      <el-table
        :data="tableData"
        @expand='expand'
        :expand-row-keys='expendRow'
        :row-key="row => row.index"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="食品名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="餐馆名称">
                <span>{{ props.row.shopName }}</span>
              </el-form-item>
              <el-form-item label="食品 ID">
                <span>{{ props.row.item_id }}</span>
              </el-form-item>
              <el-form-item label="餐馆 ID">
                <span>{{ props.row.restaurant_id }}</span>
              </el-form-item>
              <el-form-item label="食品介绍">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="餐馆地址">
                <span>{{ props.row.restaurant_address }}</span>
              </el-form-item>
              <el-form-item label="食品评分">
                <span>{{ props.row.rating }}</span>
              </el-form-item>
              <el-form-item label="食品分类">
                <span>{{ props.row.category_id }}</span>
              </el-form-item>
              <el-form-item label="月销量">
                <span>{{ props.row.month_sales }}</span>
              </el-form-item>
              <el-form-item label="审批状态">
                <span>{{ props.row.stateStr }}</span>
              </el-form-item>
              <el-form-item label="审批结果">
                <span>{{ props.row.auditRemark }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="食品名称"
          prop="name">
        </el-table-column>
        <el-table-column
          label="食品介绍"
          prop="description">
        </el-table-column>
        <el-table-column
          label="评分"
          prop="rating">
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.state=='0'"
              size="small"
              type="primary"
              v-permission="['/business/food/audit']"
              @click="handleAudit(scope.row)">审核</el-button>
            <el-button
              size="small"
              type="info"
              v-permission="['/business/food/edit']"
              @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              v-permission="['/business/food/delete']"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
      <el-dialog title="修改食品信息"     :visible.sync="dialogFormVisible">
        <el-form :model="selectTable">
          <el-form-item label="食品名称" label-width="100px">
            <el-input v-model="selectTable.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="食品介绍" label-width="100px">
            <el-input v-model="selectTable.description"></el-input>
          </el-form-item>
          <el-form-item label="食品分类" label-width="100px">
            <el-select v-model="selectIndex" :placeholder="selectMenu.label" @change="handleSelect">
              <el-option
                v-for="item in menuOptions"
                :key="item.value"
                :label="item.label"
                :value="item.index">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="食品图片" label-width="100px">
            <el-upload
              class="avatar-uploader"

              :show-file-list="false"
              :action="fileMgrUrl"
              :headers="uploadHeaders"
              :before-upload="handleBeforeUpload"
              :on-success="handleUploadSuccess">
              <img v-if="selectTable.image_path" :src="selectTable.image" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-row style="overflow: auto; text-align: center;">
          <el-table
            :data="specs"
            style="margin-bottom: 20px;"
            :row-class-name="tableRowClassName">
            <el-table-column
              prop="specs"
              label="规格">
            </el-table-column>
            <el-table-column
              prop="packing_fee"
              label="包装费">
            </el-table-column>
            <el-table-column
              prop="price"
              label="价格">
            </el-table-column>
            <el-table-column label="操作" >
              <template slot-scope="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="deleteSpecs(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="specsFormVisible = true" style="margin-bottom: 10px;">添加规格</el-button>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateFood">确 定</el-button>
        </div>
      </el-dialog>


      <el-dialog title="添加规格"  :visible.sync="specsFormVisible" >
        <el-form :rules="specsFormrules" :model="specsForm">
          <el-form-item label="规格" label-width="100px" prop="specs">
            <el-input v-model="specsForm.specs" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="包装费" label-width="100px">
            <el-input-number v-model="specsForm.packing_fee" :min="0" :max="100"></el-input-number>
          </el-form-item>
          <el-form-item label="价格" label-width="100px">
            <el-input-number v-model="specsForm.price" :min="0" :max="10000"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="specsFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addspecs">确 定</el-button>
        </div>
      </el-dialog>

        <el-dialog
          title="食品审核"
          :visible.sync="audit.show"
          width="30%">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入拒绝原因"
            v-model="audit.auditRemark">
          </el-input>
          <span slot="footer" class="dialog-footer">
            <el-button type="danger" @click="handleAuditConfirm(-1)">拒绝</el-button>
            <el-button type="primary" @click="handleAuditConfirm(1)">通过</el-button>
          </span>
        </el-dialog>


    </div>
  </div>
</template>

<script src="./food.js"></script>

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
  .table_container{
    padding: 20px;
  }
  .Pagination{
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

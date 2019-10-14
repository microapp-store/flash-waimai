<template>
  <div class="app-container">
    <div class="block">
      <div class="table_container">
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="店铺名称">
                  <span>{{ props.row.name }}</span>
                </el-form-item>
                <el-form-item label="店铺地址">
                  <span>{{ props.row.address }}</span>
                </el-form-item>
                <el-form-item label="店铺介绍">
                  <span>{{ props.row.description }}</span>
                </el-form-item>
                <el-form-item label="店铺 ID">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="联系电话">
                  <span>{{ props.row.phone }}</span>
                </el-form-item>
                <el-form-item label="评分">
                  <span>{{ props.row.rating }}</span>
                </el-form-item>
                <el-form-item label="销售量">
                  <span>{{ props.row.recent_order_num }}</span>
                </el-form-item>
                <el-form-item label="分类">
                  <span>{{ props.row.category }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            label="店铺名称"
            prop="name">
          </el-table-column>
          <el-table-column
            label="店铺地址"
            prop="address">
          </el-table-column>
          <el-table-column
            label="店铺介绍"
            prop="description">
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.$index, scope.row)">编辑
              </el-button>
              <el-button
                size="mini"
                type="success"
                @click="addFood(scope.$index, scope.row)">添加食品
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
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
        <el-dialog title="修改店铺信息"
                   :visible.sync="dialogFormVisible"
        >
          <el-form :model="selectTable">

            <el-form-item label="店铺名称" label-width="100px">
              <el-input v-model="selectTable.name" auto-complete="off"></el-input>
            </el-form-item>

            <el-form-item label="详细地址" label-width="100px">
              <el-autocomplete
                v-model="address.address"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入地址"
                style="width: 100%;"
                @select="addressSelect"
              ></el-autocomplete>
              <span>当前城市：{{city.name}}</span>
            </el-form-item>
            <el-form-item label="店铺介绍" label-width="100px">
              <el-input v-model="selectTable.description"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" label-width="100px">
              <el-input v-model="selectTable.phone"></el-input>
            </el-form-item>
            <el-form-item label="店铺分类" label-width="100px">
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
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateShop">确 定</el-button>
          </div>
        </el-dialog>

      </div>
    </div>
  </div>
</template>

<script src="./shop.js"></script>


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

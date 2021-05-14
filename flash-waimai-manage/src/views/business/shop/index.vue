<template>
  <div class="app-container">
    <div class="block">
      <el-row :gutter="20" v-permission="['/business/shop/audit']">
        <el-col :span="6">
          <el-input v-model="listQuery.name" placeholder="店铺名称"></el-input>
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

      <div class="table_container" v-permission="['/business/shop/audit']">
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
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
                <el-form-item label="商铺地点">
                  <span>{{ props.row.isInSchool =='1'?'校内':'校外' }}</span>
                </el-form-item>
                <el-form-item label="审批状态">
                  <span>{{ props.row.stateStr }}</span>
                </el-form-item>
                <el-form-item label="审批结果">
                  <span>{{ props.row.auditRemark }}</span>
                </el-form-item>
                <el-form-item label="未结算交易额(元)">
                  <span>{{ props.row.unliquidatedAmount }}</span>
                </el-form-item>
                <el-form-item label="已结算交易额(元)">
                  <span>{{ props.row.totalAmount }}</span>
                </el-form-item>
                <el-form-item label="平台收取费率(%)">
                  <span>{{ props.row.platform_rate }}</span>
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
          <el-table-column label="操作" width="450">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.state=='0'"
                size="mini"
                type="primary"
                v-permission="['/business/shop/audit']"
                @click="handleAudit(scope.$index, scope.row)">审核
              </el-button>
              <el-button
                size="mini"
                type="warning"
                v-permission="['/business/shop/audit']"
                @click="handleCheck(scope.$index, scope.row)">结算
              </el-button>
              <el-button
                v-if="scope.row.disabled==0"
                size="mini"
                type="danger"
                v-permission="['/business/shop/audit']"
                @click="handleStop(scope.$index, scope.row,1)">停用
              </el-button>
              <el-button
                v-if="scope.row.disabled==1"
                size="mini"
                type="primary"
                v-permission="['/business/shop/audit']"
                @click="handleStop(scope.$index, scope.row,0)">启用
              </el-button>
              <el-button

                size="mini"
                type="primary"
                v-permission="['/business/shop/edit']"
                @click="handleEdit(scope.$index, scope.row)">编辑
              </el-button>
              <el-button
                size="mini"
                type="success"
                v-permission="['/business/food/edit']"
                @click="addFood(scope.$index, scope.row)">添加食品
              </el-button>
              <el-button
                size="mini"
                type="info"

                @click="viewFood(scope.$index, scope.row)">查看食品
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

      </div>



      <el-dialog
        title="商铺审核"
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

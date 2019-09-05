import { getApiUrl } from '@/utils/utils'
import {
  cityGuess,
  getResturants,
  getResturantsCount,
  foodCategory,
  updateResturant,
  searchplace,
  deleteResturant
} from '@/api/getData'

export default {
  data() {
    return {
      city: {},

      // offset: 0,
      // limit: 20,
      count: 0,
      baseUrl: getApiUrl(),
      tableData: [],
      currentPage: 1,
      selectTable: {},
      dialogFormVisible: false,
      categoryOptions: [],
      selectedCategory: [],
      address: {},
      total: 0,
      listQuery: {
        page: 1,
        limit: 20
      }
    }
  },
  created() {
    this.initData()
  },

  methods: {
    async initData() {
      try {
        cityGuess().then(response => {
          this.city = response.data
          getResturantsCount().then(response2 => {
            this.count = response2.count
            this.fetchData()
          })
        })
      } catch (err) {
        console.log('获取数据失败', err)
      }
    },
    async getCategory() {
      try {
        const response = await foodCategory()
        const categories = response.data
        categories.forEach(item => {
          if (item.sub_categories.length) {
            const addnew = {
              value: item.name,
              label: item.name,
              children: []
            }
            item.sub_categories.forEach((subitem, index) => {
              if (index === 0) {
                return
              }
              addnew.children.push({
                value: subitem.name,
                label: subitem.name
              })
            })
            this.categoryOptions.push(addnew)
          }
        })
      } catch (err) {
        console.log('获取商铺种类失败', err)
      }
    },
    async fetchData() {
      // const {latitude, longitude} = this.city;
      const latitude = ''
      const longitude = ''
      getResturants({ page: this.listQuery.page, limit: this.listQuery.limit }).then(response => {
        console.log(response)
        const restaurants = response.data.records
        this.total = response.data.total
        this.tableData = []
        restaurants.forEach(item => {
          const tableData = {}
          tableData.name = item.name
          tableData.address = item.address
          tableData.description = item.description
          tableData.id = item.id
          tableData.phone = item.phone
          tableData.rating = item.rating
          tableData.recent_order_num = item.recent_order_num
          tableData.category = item.category
          tableData.image_path = item.image_path
          this.tableData.push(tableData)
        })
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.offset = (val - 1) * this.limit
      this.getResturants()
    },
    fetchNext() {
      this.listQuery.page = this.listQuery.page + 1
      this.fetchData()
    },
    fetchPrev() {
      this.listQuery.page = this.listQuery.page - 1
      this.fetchData()
    },
    fetchPage(page) {
      this.listQuery.page = page
      this.fetchData()
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
    handleEdit(index, row) {
      this.selectTable = row
      this.selectTable.image = getApiUrl() + '/file/getImgStream?fileName=' + row.image_path
      console.log(this.selectTable)
      this.address.address = row.address
      this.dialogFormVisible = true
      this.selectedCategory = row.category.split('/')
      if (!this.categoryOptions.length) {
        this.getCategory()
      }
    },
    addFood(index, row) {
      this.$router.push({ path: 'addGoods', query: { restaurant_id: row.id } })
    },
    async handleDelete(index, row) {
      try {
        const res = await deleteResturant(row.id)
        if (res.status == 1) {
          this.$message({
            type: 'success',
            message: '删除店铺成功'
          })
          this.tableData.splice(index, 1)
        } else {
          throw new Error(res.message)
        }
      } catch (err) {
        this.$message({
          type: 'error',
          message: err.message
        })
        console.log('删除店铺失败')
      }
    },
    async querySearchAsync(queryString, cb) {
      if (queryString) {
        try {
          const cityList = await searchplace(this.city.id, queryString)
          if (cityList instanceof Array) {
            cityList.map(item => {
              item.value = item.address
              return item
            })
            cb(cityList)
          }
        } catch (err) {
          console.log(err)
        }
      }
    },
    addressSelect(vale) {
      const { address, latitude, longitude } = vale
      this.address = { address, latitude, longitude }
    },
    handleServiceAvatarScucess(res, file) {
      if (res.status == 1) {
        this.selectTable.image_path = res.image_path
      } else {
        this.$message.error('上传图片失败！')
      }
    },
    beforeAvatarUpload(file) {
      const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isRightType) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isRightType && isLt2M
    },
    async updateShop() {
      this.dialogFormVisible = false
      try {
        Object.assign(this.selectTable, this.address)
        this.selectTable.category = this.selectedCategory.join('/')
        console.log(this.selectTable)
        updateResturant(this.selectTable).then(response => {
          this.$message({
            type: 'success',
            message: '更新店铺信息成功'
          })
          this.getResturants()
        })

      } catch (err) {
        console.log('更新餐馆信息失败', err)
        this.$message({
          type: 'error',
          message: err
        })
      }
    }
  }
}

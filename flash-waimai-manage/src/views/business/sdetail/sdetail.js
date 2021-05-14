import {getApiUrl} from '@/utils/utils'
import {getToken} from '@/utils/auth'
import {cityGuess} from '@/api/getData'
import permission from '@/directive/permission/index.js'
import {
  getResturants,
  getResturantsCount,
  foodCategory,
  updateResturant,
  stopResturant,
  auditResturant,
  searchplace,
  deleteResturant
} from '@/api/business/shop'

export default {
  directives: {permission},
  data() {
    return {
      city: {},
      count: 0,
      fileMgrUrl: getApiUrl() + '/file',
      uploadHeaders: {
        'Authorization': ''
      },
      tableData: [],
      currentPage: 1,
      selectTable: {},
      dialogFormVisible: false,
      categoryOptions: [],
      selectedCategory: [],
      address: {},
      total: 0,
      stateList: [
        {label: '审核中', value: '0'},
        {label: '审核通过', value: '1'},
        {label: '审核拒绝', value: '-1'},
      ],
      audit: {show: false, auditRemark: ''},
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        state: ''
      }
    }
  },
  created() {
    this.initData()
    this.uploadHeaders['Authorization'] = getToken()
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
      getResturants(this.listQuery).then(response => {
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
          tableData.state = item.state
          tableData.stateStr = item.stateStr
          tableData.auditRemark = item.auditRemark
          tableData.disabled = item.disabled
          tableData.unliquidatedAmount = item.unliquidatedAmount
          tableData.totalAmount = item.totalAmount
          tableData.platform_rate = item.platform_rate
          this.selectTable = tableData
          this.selectTable.image = getApiUrl() + '/file/getImgStream?fileName=' + tableData.image_path
          this.address.address = tableData.address

          this.selectedCategory = tableData.category.split('/')
          if (!this.categoryOptions.length) {
            this.getCategory()
          }
        })
      })
    },

    search() {
      this.fetchData()
    },
    addFood( row) {
      this.$router.push({path: '/business/food/add', query: {restaurant_id: row.id}})
    },
    viewFood( row) {
      this.$router.push({path: '/business/food', query: {restaurant_id: row.id}})
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
      const {address, latitude, longitude} = vale
      this.address = {address, latitude, longitude}
    },
    handleServiceAvatarScucess(res, file) {
      this.selectTable.image_path = res.data.realFileName
      this.selectTable.image = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName

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
      try {
        Object.assign(this.selectTable, this.address)
        this.selectTable.category = this.selectedCategory.join('/')
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

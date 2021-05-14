import { getApiUrl } from '@/utils/utils'
import { getToken } from '@/utils/auth'
import { cityGuess, searchplace } from '@/api/getData'
import { addShop, foodCategory } from '@/api/business/shop'

export default {
  data() {
    return {
      city: {},
      fileMgrUrl: getApiUrl() + '/file',
      uploadHeaders: {
        'Authorization': ''
      },
      formData: {
        name: '', //店铺名称
        address: '', //地址
        latitude: '',
        longitude: '',
        description: '', //介绍
        phone: '',
        promotion_info: '',
        float_delivery_fee: 5, //运费
        float_minimum_order_amount: 20, //起价
        is_premium: true,
        deliveryMode: true,
        news: true,
        bao: true,
        zhun: true,
        piao: true,
        startTime: '',
        endTime: '',
        image_path: '',
        business_license_image: '',
        catering_service_license_image: '',
        platform_rate:'10'

      },
      rules: {
        name: [
          { required: true, message: '请输入店铺名称', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话' },
          { type: 'number', message: '电话号码必须是数字' }
        ]
      },
      options: [{
        value: '满减优惠',
        label: '满减优惠'
      }, {
        value: '优惠大酬宾',
        label: '优惠大酬宾'
      }, {
        value: '新用户立减',
        label: '新用户立减'
      }, {
        value: '进店领券',
        label: '进店领券'
      }],
      activityValue: '满减优惠',
      activities: [{
        icon_name: '减',
        name: '满减优惠',
        description: '满30减5，满60减8'
      }],
      categoryOptions: [],
      selectedCategory: ['快餐便当', '简餐']
    }
  },
  mounted() {
    this.initData()
    this.uploadHeaders['Authorization'] = getToken()
  },
  methods: {
    async initData() {
      try {
        const cityResponse = await cityGuess()
        this.city = cityResponse.data
        const categoriesResponse = await foodCategory()
        const categories = categoriesResponse.data
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
        console.log(err)
      }
    },
    async querySearchAsync(queryString, cb) {
      if (queryString) {
        try {
          const cityListResponse = await searchplace(this.city.id, queryString)
          const cityList = cityListResponse.data
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
    addressSelect(address) {
      this.formData.latitude = address.latitude
      this.formData.longitude = address.longitude
    },
    handleShopAvatarScucess(res, file) {
      this.formData.imagePath = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
      this.formData.image_path = res.data.realFileName
    },
    handleBusinessAvatarScucess(res, file) {
      this.formData.business_license_image = res.data.realFileName
      this.formData.businessLicenseImage = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
    },
    handleServiceAvatarScucess(res, file) {
      this.formData.catering_service_license_image = res.data.realFileName
      this.formData.cateringServiceLicenseImage = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
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
    tableRowClassName(row, index) {
      if (index === 1) {
        return 'info-row'
      } else if (index === 3) {
        return 'positive-row'
      }
      return ''
    },
    selectActivity() {
      this.$prompt('请输入活动详情', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        if (value === null) {
          this.$message({
            type: 'info',
            message: '请输入活动详情'
          })
          return
        }
        let newObj = {}
        switch (this.activityValue) {
          case '满减优惠':
            newObj = {
              icon_name: '减',
              name: '满减优惠',
              description: value
            }
            break
          case '优惠大酬宾':
            newObj = {
              icon_name: '特',
              name: '优惠大酬宾',
              description: value
            }
            break
          case '新用户立减':
            newObj = {
              icon_name: '新',
              name: '新用户立减',
              description: value
            }
            break
          case '进店领券':
            newObj = {
              icon_name: '领',
              name: '进店领券',
              description: value
            }
            break
        }
        this.activities.push(newObj)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    handleDelete(index) {
      this.activities.splice(index, 1)
    },
    submitForm(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          Object.assign(this.formData, { activities: this.activities }, {
            category: this.selectedCategory.join('/')
          })
          const activities = this.formData.activities
          const activitiesJson = JSON.stringify(activities)
          delete this.formData.activities
          this.formData.activitiesJson = activitiesJson
          addShop(this.formData).then(response => {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.formData = {
              name: '', //店铺名称
              address: '', //地址
              latitude: '',
              longitude: '',
              description: '', //介绍
              phone: '',
              promotion_info: '',
              float_delivery_fee: 5, //运费
              float_minimum_order_amount: 20, //起价
              is_premium: true,
              deliveryMode: true,
              news: true,
              bao: true,
              zhun: true,
              piao: true,
              startTime: '',
              endTime: '',
              image_path: '',
              business_license_image: '',
              catering_service_license_image: ''
            }
            this.selectedCategory = ['快餐便当', '简餐']
            this.activities = [{
              icon_name: '减',
              name: '满减优惠',
              description: '满30减5，满60减8'
            }]
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '请检查输入是否正确',
            offset: 100
          })
          return false
        }
      })
    }
  }
}

import { getApiUrl } from '@/utils/utils'
import { getToken } from '@/utils/auth'
import { addFood } from '@/api/business/food'
import { getCategory, addCategory } from '@/api/business/shop'

export default {
  data() {
    return {
      restaurant_id: 1,
      categoryForm: {
        categoryList: [],
        categorySelect: '',
        name: '',
        description: ''
      },
      fileMgrUrl: getApiUrl() + '/file',
      uploadHeaders: {
        'Authorization': ''
      },
      foodForm: {
        name: '',
        description: '',
        image_path: '',
        activity: '',
        attributes: [],
        specs: [{
          specs: '默认',
          packing_fee: 0,
          price: 20
        }]
      },
      categoryRules: {
        name: [
          { required: true, message: '请选择食品种类', trigger: 'blur' }
        ]
      },
      foodrules: {
        name: [
          { required: true, message: '请输入食品名称', trigger: 'blur' }
        ],
        attributes: [
          { required: true, message: '请选择食品特点', trigger: 'blur' }
        ],
        categoryName: [
          { required: true, message: '请选择食品种类', trigger: 'blur' }
        ],
      },
      attributes: [{
        value: '新',
        label: '新品'
      }, {
        value: '招牌',
        label: '招牌'
      }],
      showAddCategory: false,
      foodSpecs: 'one',
      dialogFormVisible: false,
      specsForm: {
        specs: '',
        packing_fee: 0,
        price: 20
      },
      specsFormrules: {
        specs: [
          { required: true, message: '请输入规格', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.uploadHeaders['Authorization'] = getToken()
    if (this.$route.query.restaurant_id) {
      this.restaurant_id = this.$route.query.restaurant_id
    } else {
      this.restaurant_id = Math.ceil(Math.random() * 10)
      this.$msgbox({
        title: '提示',
        message: '添加食品需要选择一个商铺，先去就去选择商铺吗？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        beforeClose: (action, instance, done) => {
          if (action === 'confirm') {
            this.$router.push('/business/shop')
            done()
          } else {
            this.$message({
              type: 'info',
              message: '取消'
            })
            done()
          }
        }
      })
    }
    this.initData()
  },
  computed: {
    selectValue: function() {
      return this.categoryForm.categoryList[this.categoryForm.categorySelect] || {}
    }
  },
  methods: {
    async initData() {
      try {
        const result = await getCategory(this.restaurant_id)
        if (result.code === 20000) {
          result.category_list.map((item, index) => {
            item.value = index
            item.label = item.name
          })
          this.categoryForm.categoryList = result.category_list
        } else {
          console.log(result)
        }
      } catch (err) {
        console.log(err)
      }
    },
    addCategoryFun() {
      this.showAddCategory = !this.showAddCategory
    },
    submitcategoryForm(formName) {
      console.log(this.categoryForm)
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          console.log(2,this.categoryForm)
          const params = {
            name: this.categoryForm.name,
            description: this.categoryForm.description,
            restaurant_id: this.restaurant_id
          }
          try {
            const result = await addCategory(params)
            if (result.code === 20000) {
              this.initData()
              this.categoryForm.name = ''
              this.categoryForm.description = ''
              this.showAddCategory = false
              this.$message({
                type: 'success',
                message: '添加成功'
              })
            }
          } catch (err) {
            console.log(err)
          }
        } else {
          this.$notify.error({
            title: '错误',
            message: '请检查输入是否正确',
            offset: 100
          })
          return false
        }
      })
    },
    uploadImg(res, file) {
      this.foodForm.imagePath = getApiUrl() + '/file/getImgStream?fileName=' + res.data.realFileName
      this.foodForm.image_path = res.data.realFileName
    },
    beforeImgUpload(file) {
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
    addspecs() {
      this.foodForm.specs.push({ ...this.specsForm })
      this.specsForm.specs = ''
      this.specsForm.packing_fee = 0
      this.specsForm.price = 20
      this.dialogFormVisible = false
    },
    handleDelete(index) {
      this.foodForm.specs.splice(index, 1)
    },
    tableRowClassName(row, index) {
      if (index === 1) {
        return 'info-row'
      } else if (index === 3) {
        return 'positive-row'
      }
      return ''
    },
    addFood(foodForm) {
      console.log(this.selectValue.id)
      this.$refs[foodForm].validate(async(valid) => {
        if (valid) {

          const params = {
            ...this.foodForm,
            category_id: this.selectValue.id,
            restaurant_id: this.restaurant_id
          }
          try {
            const me = this
            const result = await addFood({
              name: me.foodForm.name,
              descript: me.foodForm.description,
              idShop: me.restaurant_id,
              category_id: me.selectValue.id,
              image_path: me.foodForm.image_path,
              activity: me.foodForm.activity,
              attributesJson:JSON.stringify(me.foodForm.attributes),
              specsJson: JSON.stringify(me.foodForm.specs)
            })
            if (result.code === 20000) {
              console.log(result)
              this.$message({
                type: 'success',
                message: '添加成功'
              })
              this.foodForm = {
                name: '',
                description: '',
                image_path: '',
                activity: '',
                attributes: [],
                specs: [{
                  specs: '默认',
                  packing_fee: 0,
                  price: 20
                }]
              }
            } else {
              this.$message({
                type: 'error',
                message: result.message
              })
            }
          } catch (err) {
            console.log(err)
          }
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

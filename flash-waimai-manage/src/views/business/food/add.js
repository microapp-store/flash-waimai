import { getApiUrl } from '@/utils/utils'
import { getCategory, addCategory, addFood } from '@/api/business/food'

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
      foodrules: {
        name: [
          { required: true, message: '请输入食品名称', trigger: 'blur' }
        ]
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
            this.$router.push('/data/shop')
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
        if (result.status == 1) {
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
    submitcategoryForm(categoryForm) {
      this.$refs[categoryForm].validate(async(valid) => {
        if (valid) {
          const params = {
            name: this.categoryForm.name,
            description: this.categoryForm.description,
            restaurant_id: this.restaurant_id
          }
          try {
            const result = await addCategory(params)
            if (result.status == 1) {
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
      if (res.status == 1) {
        this.foodForm.image_path = res.image_path
      } else {
        this.$message.error('上传图片失败！')
      }
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
      this.$refs[foodForm].validate(async(valid) => {
        if (valid) {
          const params = {
            ...this.foodForm,
            category_id: this.selectValue.id,
            restaurant_id: this.restaurant_id
          }
          try {
            const result = await addFood(params)
            if (result.status == 1) {
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

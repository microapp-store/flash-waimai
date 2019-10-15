import { getList } from '@/api/business/order'
import { getResturantDetail } from '@/api/business/shop'
import { getUserInfo } from '@/api/business/user'
import { getAddressById } from '@/api/business/address'

export default {
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        restaurant_id:undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      expandRowData: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {
      this.list = []
      this.listLoading = true
      getList(this.listQuery).then(response => {
        let list = response.data.records
        list.forEach((item,index) =>{
            item.index = index
        })
        this.list = list
        console.log('list',list)
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.id = ''
      this.listQuery.restaurant_id = ''
      this.fetchData()
    },
    async expandRow(row, status){
      if(status.length>0) {
        const restaurant = await getResturantDetail(row.restaurant_id);
        const userInfo = await getUserInfo(row.user_id);
        const addressInfo = await getAddressById(row.address_id);
        this.list.splice(row.index, 1, {
          ...row, ...{
            restaurant_name: restaurant.data.name,
            restaurant_address: restaurant.data.address,
            address: addressInfo.data.address,
            user_name: userInfo.data.username
          }
        });
        this.expandRowData = []
        this.expandRowData.push(row.index);
      }else{
        this.expandRowData = []
      }


    },
    getRowKeys(row){
      return row.index
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
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
    clear() {
      this.$confirm('确定清空数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        clear().then(response => {
          this.$message({
            message: '清空成功',
            type: 'sucess'
          })
          this.fetchData()
        })
      }).catch(() => {
      })
    }

  }
}

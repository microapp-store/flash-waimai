import request from '@/utils/request'

/**
 * 获取搜索地址
 */
export function searchplace(cityid, value) {
  return request({
    url: '/v1/pois',
    method: 'get',
    params: {
      type: 'search',
      city_id: cityid,
      keyword: value
    }
  })
}

/**
 * category 种类列表
 */

export function foodCategory(params) {
  return request({
    url: '/shopping/v2/restaurant/category',
    method: 'get'
  })
}

/**
 * 获取餐馆列表
 */

export function getResturants(params) {
  return request({
    url: '/shopping/listShop',
    method: 'get',
    params
  })
}

/**
 * 获取餐馆数量
 */
export function getResturantsCount(params) {
  return request({
    url: '/shopping/restaurants/count',
    method: 'get'
  })
}

/**
 * 添加商铺
 */

export function addShop(params) {
  return request({
    url: '/shopping/addShop',
    method: 'post',
    params
  })
}

/**
 * 更新餐馆信息
 */

export function updateResturant(params) {
  return request({
    url: '/shopping/updateshop',
    method: 'post',
    params
  })
}

/**
 * 结算金额
 * @param params
 */
export function check(params) {
  return request({
    url: '/shopping/check',
    method: 'post',
    params
  })
}
/**
 * 审核商铺
 * @param params
 */
export function auditResturant(params) {
  return request({
    url: '/shopping/auditShop',
    method: 'post',
    params
  })
}

/**
 * 停用商铺
 * @param params
 */
export function stopResturant(params) {
  return request({
    url: '/shopping/stopShop',
    method: 'post',
    params
  })
}
/**
 * 删除餐馆
 */

export function deleteResturant(id) {
  return request({
    url: '/shopping/restaurants/' + id,
    method: 'delete'
  })
}

/**
 * 获取餐馆详细信息
 */

export function getResturantDetail(id) {
  return request({
    url: '/shopping/restaurant/' + id,
    method: 'get'
  })
}

/**
 * 获取menu列表
 */

export function getMenu(params) {
  return request({
    url: '/shopping/v2/menu/',
    method: 'get',
    params
  })
}

/**
 * 获取menu详情
 */

export function getMenuById(category_id) {
  return request({
    url: '/shopping/v2/menu/' + category_id,
    method: 'get'
  })
}

/**
 * 获取当前店铺食品种类
 */

export function getCategory(restaurant_id) {
  return request({
    url: '/shopping/getcategory/' + restaurant_id,
    method: 'get'
  })
}

/**
 * 添加食品种类
 */

export function addCategory(params) {
  return request({
    url: '/shopping/addcategory',
    method: 'post',
    params
  })
}

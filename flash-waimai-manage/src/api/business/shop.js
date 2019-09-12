import request from '@/utils/request'

/**
 * 获取搜索地址
 */

// export const searchplace = (cityid, value) => fetch('/v1/pois', {
// 	type: 'search',
// 	city_id: cityid,
// 	keyword: value
// });

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

// export const foodCategory = (latitude, longitude) => fetch('/shopping/v2/restaurant/category');

export function foodCategory(params) {
  return request({
    url: '/shopping/v2/restaurant/category',
    method: 'get'
  })
}

/**
 * 获取餐馆列表
 */

// export const getResturants = data => fetch('/shopping/restaurants', data);

export function getResturants(params) {
  return request({
    url: '/shopping/restaurants',
    method: 'get',
    params
  })
}

/**
 * 获取餐馆数量
 */
// export const getResturantsCount = () => fetch('/shopping/restaurants/count');

export function getResturantsCount(params) {
  return request({
    url: '/shopping/restaurants/count',
    method: 'get'
  })
}

/**
 * 添加商铺
 */

// export const addShop = data => fetch('/shopping/addShop', data, 'POST')
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

// export const updateResturant = data => fetch('/shopping/updateshop', data, 'POST');
export function updateResturant(params) {
  return request({
    url: '/shopping/updateshop',
    method: 'post',
    params
  })
}

/**
 * 删除餐馆
 */

// export const deleteResturant = restaurant_id => fetch('/shopping/restaurant/' + restaurant_id, {}, 'DELETE');

export function deleteResturant(id) {
  return request({
    url: '/shopping/restaurant/' + id,
    method: 'delete'
  })
}

/**
 * 获取餐馆详细信息
 */

// export const getResturantDetail = restaurant_id => fetch('/shopping/restaurant/' + restaurant_id);
export function getResturantDetail(id) {
  return request({
    url: '/shopping/restaurant/' + id,
    method: 'get'
  })
}

/**
 * 获取menu列表
 */

// export const getMenu = data => fetch('/shopping/v2/menu', data);
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

// export const getMenuById = category_id => fetch('/shopping/v2/menu/' + category_id);
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

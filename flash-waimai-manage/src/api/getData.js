import request from '@/utils/request'

/**
 * 获取定位城市
 */

// export const cityGuess = () => fetch('/v1/cities', {
// 	type: 'guess'
// });

export function cityGuess() {
  return request({
    url: '/v1/cities',
    method: 'get',
    params: {
      type: 'guess'
    }
  })
}

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
 * 获取食品列表
 */

// export const getFoods = data => fetch('/shopping/v2/foods', data)
export function getFoods(params) {
  return request({
    url: '/shopping/v2/foods',
    method: 'get',
    params
  })
}

/**
 * 更新食品信息
 */

// export const updateFood = data => fetch('/shopping/v2/updatefood', data, 'POST');
export function updateFood(params) {
  return request({
    url: '/shopping/v2/updatefood',
    method: 'post',
    params
  })
}

import request from '@/utils/request'

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

/**
 * 删除食品
 */

// export const deleteFood = food_id => fetch('/shopping/v2/food/' + food_id, {}, 'DELETE');
export function deleteFood(id) {
  return request({
    url: '/shopping/v2/food/' + id,
    method: 'delete'
  })
}

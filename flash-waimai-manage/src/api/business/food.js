import request from '@/utils/request'

/**
 * 添加食品
 */

export function addFood(params) {
  return request({
    url: '/shopping/addfood',
    method: 'post',
    params
  })
}
/**
 * 获取食品列表
 */

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

export function deleteFood(id) {
  return request({
    url: '/shopping/v2/food/' + id,
    method: 'delete'
  })
}

/**
 * 审核商铺
 * @param params
 */
export function auditFood(params) {
  return request({
    url: '/shopping/auditFood',
    method: 'post',
    params
  })
}

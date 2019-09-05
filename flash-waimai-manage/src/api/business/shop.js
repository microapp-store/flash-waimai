import request from '@/utils/request'

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

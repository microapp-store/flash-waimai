import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/bos/orders',
    method: 'get',
    params
  })
}

export function updateOrderStatus(params) {
  return request({
    url: '/bos/updateOrderStatus',
    method: 'post',
    params
  })
}

export function getOrder(params) {
  return request({
    url: '/bos/getOrder',
    method: 'get',
    params
  })
}

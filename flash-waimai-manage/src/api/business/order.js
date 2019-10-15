import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/bos/orders',
    method: 'get',
    params
  })
}

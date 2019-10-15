import request from '@/utils/request'

export function getAddressById(id) {
  return request({
    url: '/address/'+id,
    method: 'get'
  })
}

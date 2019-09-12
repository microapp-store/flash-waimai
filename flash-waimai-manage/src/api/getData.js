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

export function searchplace(cityid, value) {
  return request({
    url: '/v1/pois',
    type: 'search',
    params: {
      city_id: cityid,
      keyword: value
    }
  })
}

import request from '@/utils/request'
const groupName = 'shopCoupon'
export default {
  /**
   * 添加
   */
  save(sysUser) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: sysUser
    })
  },
  /**
   * 分页查询
   * @param {分页查询} page
   */
  getByPage(page) { // 分页查询
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  /**
   * 删除
   * @param {*} id
   */
  delete(id) {
    return request({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 下架
   */
  down(id) {
    return request({
      url: `/${groupName}/down/${id}`,
      method: 'put'
    })
  }
}

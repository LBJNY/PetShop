import request from '@/utils/request'
const groupName = 'sysNotice'
export default {
  /**
   * 添加
   */
  save(sysNotice) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: sysNotice
    })
  },
  /**
  * 修改
  */
  update(sysNotice) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: sysNotice
    })
  },
  /**
   * 分页
   */
  getByPage(page) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  /**
   * 启用
   */
  enableById(id) {
    return request({
      url: `/${groupName}/enable/${id}`,
      method: 'put'
    })
  },
  /**
   * 禁用
   */
  disableById(id) {
    return request({
      url: `/${groupName}/disable/${id}`,
      method: 'put'
    })
  },
  /**
   * 删除
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 根据id查询
   */
  get(id) {
    return request({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  }
}

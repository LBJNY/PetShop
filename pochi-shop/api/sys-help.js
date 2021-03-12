import request from '@/utils/request'
var groupName = 'sysHelp'
export default {
  /**
   * 分页查询
   * @param {*} page
   */
  getByPage(page) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  /**
   * 保存
   * @param {*} sysHelp
  */
  save(sysHelp) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: sysHelp
    })
  },
  /**
   * 根据id查询
   * @param {*} id
  */
  get(id) {
    return request({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  },
  /**
   * 更新
   * @param {*} sysHelp
   */
  update(sysHelp) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: sysHelp
    })
  },
  /**
   * 根据id删除
   * @param {*} id
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete`,
      method: 'put',
      data: { id: id }
    })
  }
}

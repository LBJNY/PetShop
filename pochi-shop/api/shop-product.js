import request from '@/utils/request'
const groupName = 'shopProduct'
export default {
  save(shopPayOrder) { // 保存
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: shopPayOrder
    })
  },
  getByPage(page) { // 分页查询
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  getByPageHasNotPack(page) { // 分页查询 没有套装的
    return request({
      url: `/${groupName}/getByPageHasNotPack`,
      method: 'post',
      data: page
    })
  },
  /**
   * 修改
   */
  update(sysUser) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: sysUser
    })
  },
  /**
   * 查询回显数据
   * @param {*} id
   */
  getUpdate(id) {
    return request({
      url: `/${groupName}/getUpdate/${id}`,
      method: 'get'
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
   * 上架
   */
  publish(id) {
    return request({
      url: `/${groupName}/publish/${id}`,
      method: 'put'
    })
  },
  /**
   * 下架
   */
  unPublish(id) {
    return request({
      url: `/${groupName}/unPublish/${id}`,
      method: 'put'
    })
  },
  /**
   * 新品
   */
  news(id) {
    return request({
      url: `/${groupName}/news/${id}`,
      method: 'put'
    })
  },
  /**
   * 旧品
   */
  old(id) {
    return request({
      url: `/${groupName}/old/${id}`,
      method: 'put'
    })
  },
  /**
   * 推荐
   */
  recommend(id) {
    return request({
      url: `/${groupName}/recommend/${id}`,
      method: 'put'
    })
  },
  /**
   * 不推荐
   */
  unRecommend(id) {
    return request({
      url: `/${groupName}/unRecommend/${id}`,
      method: 'put'
    })
  },
  /**
   * 根据packId获取商品列表
   */
  getProductDetailList(id){
    return request({
      url: `/${groupName}/getProductDetailList/${id}`,
      method: 'get'
    })
  }
}

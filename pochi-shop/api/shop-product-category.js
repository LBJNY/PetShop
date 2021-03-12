import request from '@/utils/request'
var groupName = 'shopProductCategory'
export default {
  getByPage(page) { // 分页查询
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  save(shopProductCategory) { // 保存
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: shopProductCategory
    })
  },
  get(id) { // 根据id查询
    return request({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  },
  getTree() { // 根据id查询
    return request({
      url: `/${groupName}/getTree`,
      method: 'get'
    })
  },
  getSelectTree() { // 根据id查询
    return request({
      url: `/${groupName}/getSelectTree`,
      method: 'get'
    })
  },
  getAll() { // 根据id查询
    return request({
      url: `/${groupName}/getAll`,
      method: 'get'
    })
  },
  update(shopProductCategory) { // 更新
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: shopProductCategory
    })
  },
  deleteById(id) { // 根据id删除
    return request({
      url: `/${groupName}/delete`,
      method: 'put',
      data: { id: id }
    })
  },
  getAllSecond(){ // 查询所有二级菜单
    return request({
      url: `/${groupName}/getAllSecond`,
      method: 'get'
    })
  }
}

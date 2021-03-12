import request from '@/utils/request'
var groupName = 'sysLog'
export default {
  getByPage(page) { // 分页查询
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  get(id) { // 根据id查询
    return request({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  },
  deleteById(id) { // 根据id删除
    return request({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  }
}
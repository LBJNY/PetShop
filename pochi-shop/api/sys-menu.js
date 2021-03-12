import requset from '@/utils/request'
const groupName = 'sysMenu'
export default {
  /**
   * 添加
   */
  save(sysUser) {
    return requset({
      url: `/${groupName}/save`,
      method: 'post',
      data: sysUser
    })
  },
  /**
   * 修改
   */
  update(sysUser) {
    return requset({
      url: `/${groupName}/update`,
      method: 'put',
      data: sysUser
    })
  },
  /**
   * 分页查询
   */
  getByPage(page) {
    return requset({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },

  /**
   * 删除
   */
  deleteById(id) {
    return requset({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 根据id查询
   */
  get(id) {
    return requset({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  },
  /**
   * 查询所有
   */
  findAll() {
    return requset({
      url: `/${groupName}/getAll`,
      method: 'get'
    })
  },
  /**
   * 获取树形列表
   */
  getTreeList() {
    return requset({
      url: `/${groupName}/getTreeList`,
      method: 'get'
    })
  },
  /**
   * 获取当前角色下的所有菜单
   */
  getRoleSelectMenu(roleId) { // 查询所有
    return requset({
      url: `/${groupName}/getRoleSelectMenu/${roleId}`,
      method: 'get'
    })
  },
  /**
   * 查询用户的路由
   */
  getRouters() {
    return requset({
      url: `/${groupName}/getRouters`,
      method: 'get'
    })
  }
}

import requset from '@/utils/request'
const groupNme = 'sysRole'
export default {
  /**
   * 添加
   */
  save(sysUser){
    return requset({
      url:`/${groupNme}/save`,
      method:'post',
      data:sysUser
    })
  },
  /**
   * 修改
   */
  update(sysUser){
    return requset({
      url:`/${groupNme}/update`,
      method:'put',
      data:sysUser
    })
  },
  /**
   * 分页查询
   */
  getByPage(page){
    return requset({
      url:`/${groupNme}/getByPage`,
      method:'post',
      data:page
    })
  },

  /**
   * 删除
   */
  deleteById(id) {
    return requset({
      url: `/${groupNme}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 根据id查询
   */
  get(id) {
    return requset({
      url: `/${groupNme}/get/${id}`,
      method: 'get'
    })
  },
  /**
   * 查询所有
   */
  findAll() {
    return requset({
      url: `/${groupNme}/getAll`,
      method: 'get'
    })
  }
}

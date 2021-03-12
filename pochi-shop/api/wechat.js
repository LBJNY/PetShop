import request from '@/utils/request'
var groupName = 'wx'
export default {
	/**
	 * 根据code登录
	 * @param {Object} code
	 */
	loginByCode(code) {
		return request({
			url: `/${groupName}/wxLogin/${code}`,
			method: 'get'
		})
	},
	/**
	 * 注册
	 * @param {Object} user
	 */
	regitserLogin(user) {
		return request({
			url: `/${groupName}/registerLogin`,
			method: 'post',
			data: user
		})
	},
	/**
	 * 绑定手机号
	 * @param {Object} user
	 */
	bindUser(user) {
		return request({
			url: `/${groupName}/bindUser`,
			method: 'post',
			data: user
		})
	},
	/**
	 * 获取登录信息
	 */
	getLoginInfo() {
		return request({
			url: `/${groupName}/info`,
			method: 'get'
		})
	}
}

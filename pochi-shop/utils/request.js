import axios from 'axios'

// create an axios instance
const service = axios.create({
	baseURL: 'http://localhost:8080', // url = base url + request url
	// withCredentials: true, // send cookies when cross-domain requests
	timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
	config => {
		// do something before request is sent
		const token = uni.getStorageSync('Authorization')
		if (token) {
			// let each request carry token
			// ['X-Token'] is a custom headers key
			// please modify it according to the actual situation
			// config.headers['Authorization'] = getToken()
			config.headers['Authorization'] = token
		}
		return config
	},
	error => {
		// do something with request error
		console.log(error) // for debug
		return Promise.reject(error)
	}
)

// response interceptor
service.interceptors.response.use(
	response => {
		const res = response.data

		// if the custom code is not 20000, it is judged as an error.
		if (res.code !== 20000) {
			uni.showToast({
				title: res.msg || 'Error',
				icon: "none",
				duration: 5000
			})
			return Promise.reject(new Error(res.message || 'Error'))
		} else {
			return res
		}
	},
	error => {
		console.log('err' + error) // for debug
		uni.showToast({
			title: '操作失败',
			icon: "none",
			duration: 5000
		})
		return Promise.reject(error)
	}
)

service.defaults.adapter = function(config) {
	return new Promise((resolve, reject) => {
		var settle = require('axios/lib/core/settle');
		var buildURL = require('axios/lib/helpers/buildURL');
		uni.request({
			method: config.method.toUpperCase(),
			url: config.baseURL + buildURL(config.url, config.params, config.paramsSerializer),
			header: config.headers,
			data: config.data,
			dataType: config.dataType,
			responseType: config.responseType,
			sslVerify: config.sslVerify,
			complete: function complete(response) {
				response = {
					data: response.data,
					status: response.statusCode,
					errMsg: response.errMsg,
					header: response.header,
					config: config
				};
				settle(resolve, reject, response);
			}
		})
	})
}


export default service

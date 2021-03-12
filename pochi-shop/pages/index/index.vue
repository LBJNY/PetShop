<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{title}}</text>
		</view>
		<vus-layer></vus-layer>
	</view>
</template>

<script>
	import weChatApi from '@/api/wechat.js'
	export default {
		data() {
			return {
				title: '喵喵屋'
			}
		},
		//页面显示触发
		onShow() {
			// 登录获取
			uni.login({
				provider: "weixin",
				success: (res) => {
					console.log(res)
					// 获取微信登录用的code
					const code = res.code
					weChatApi.loginByCode(code).then(res => {
						if (res.data.token) {
							weChatApi.getLoginInfo().then(res => {
								uni.setStorageSync('loginUser', res.data)
							})
							uni.setStorageSync('Authorization', res.data.token)
						} else {
							uni.setStorageSync('openId', res.data.openId)
							console.log("openid:" + res.data.openId)
						}
						uni.switchTab({
							url: '/pages/home/home'
						})
					})
				}
			})
		},
		methods: {

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>

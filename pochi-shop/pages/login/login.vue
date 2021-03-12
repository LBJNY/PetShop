<template>
	<view class="page">
		<view class="logo">
			<image src="../../static/logo.png" mode=""></image>
		</view>
		<!-- 填写区 -->
		<view class="input-info">
			<view class="info">
				<input type="tel" maxlength="11" v-model="form.phone" placeholder="手机号">
				<view class="more"></view>
			</view>
			<view class="info" :style="isLoginWay?'':'display: none'">
				<input type="tel" v-model="form.code" maxlength="6" placeholder="请输入验证码">
				<view class="more">
					<text class="mo">获取验证码</text>
					<text class="mo" style="display: none">59秒后重试</text>
				</view>
			</view>
			<view class="info" :style="isLoginWay?'display: none':''">
				<input :password='!isPassword' v-model="form.password" maxlength="26" placeholder="请输入密码">
				<view class="more">
					<text class="iconfont" :class="isPassword?'icon-eye-on':'icon-eye-off'"
						@click="isPassword = !isPassword"></text>
					<text class="mo">忘记密码</text>
				</view>
			</view>
		</view>
		<!-- 按钮 -->
		<view class="btn-info">
			<view class="btn" :style="isLogin?'opacity:1':'opacity:0.4'" @click="isLogin?onLogin():''">
				<text>登录</text>
			</view>
		</view>
		<!-- 操作 -->
		<view class="operation">
			<text @click="onLoginCut">{{isLoginWay?'手机号密码登录':'短信验证码登录'}}</text>
			<text @click="onRegister">新用户注册</text>
		</view>
		<!-- 其他方式登录 -->
		<view class="other-ways">
			<text>其他登录方式</text>
		</view>
		<!-- 登录方式 -->
		<view class="login-way">
			<button class="way" open-type="getUserInfo" @getuserinfo="getUserInfo">
				<image src="/static/wx_ico.png" mode=""></image>
				<text>微信登录</text>
			</button>
		</view>
	</view>
</template>
<script>
	import weChatApi from '@/api/wechat.js'
	import md5 from 'js-md5'
	export default {
		data() {
			return {
				isPassword: false,
				isRegister: false,
				TabCur: 1,
				// 表单
				form: {
					phone: '',
					code: '',
					password: '',
				},
			};
		},
		methods: {
			//注册
			onRegister() {
				uni.navigateTo({
					url: '/pages/register/register'
				})
			},
			//切换绑定方式
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
			},
			//获取用户信息
			getUserInfo(detail) {
				const userInfo = detail.detail.userInfo
				console.log(detail)
				// 获取到openId
				const openId = uni.getStorageSync('openId')
				userInfo.openId = openId
				weChatApi.regitserLogin(userInfo).then(res => {
					uni.showToast({
						title: "登录成功"
					})
					uni.setStorageSync('Authorization', res.data.token)
					console.log(res.data)
					uni.reLaunch({
						url: '/pages/register/register'
					})
				})

			},
			/**
			 * 登录切换
			 */
			onLoginCut() {
				this.isLoginWay = !this.isLoginWay;
				// 验证码
				if (this.isLoginWay) {
					this.isLogin = this.form.code && this.form.phone ? true : false;
				}
				// 账号密码
				if (!this.isLoginWay) {
					this.isLogin = this.form.password && this.form.phone ? true : false;
				}
			},
			/**
			 * 登录点击
			 */
			onLogin() {
				this.form.bindType = this.TabCur
				const user = {
					...this.form
				}
				user.password = md5(user.password)
				weChatApi.bindUser(user).then(res => {
					uni.showToast({
						title: res.msg
					})
					uni.switchTab({
						url: "/pages/home/home"
					})
				})
			},

		},
		watch: {
			form: {
				handler(newValue, oldValue) {
					// 验证码
					if (this.isLoginWay) {
						this.isLogin = newValue.code && newValue.phone ? true : false;
					}
					// 账号密码
					if (!this.isLoginWay) {
						this.isLogin = newValue.password && newValue.phone ? true : false;
					}
				},
				deep: true
			}
		}
	}
</script>

<style scoped lang="scss">
	@import 'login.scss';

	button {
		// 按钮透明色
		background-color: transparent;

		&::after {
			border: 0rpx;
		}
	}
</style>

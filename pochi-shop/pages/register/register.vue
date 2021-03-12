<template>
	<view class="page">

		<scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item flex-sub" @tap="tabSelect" :class="1===TabCur?'text-orange cur':''" :data-id="1">
					绑定已有账号
				</view>
				<view class="cu-item flex-sub" @tap="tabSelect" :class="2===TabCur?'text-orange cur':''" :data-id="2">
					绑定新账号
				</view>
			</view>
		</scroll-view>

		<!-- 填写区 -->
		<view class="input-info">
			<view class="info">
				<input type="tel" v-model="form.phone" maxlength="11" placeholder="手机号">
				<view class="more">

				</view>
			</view>
			<!--     <view class="info">
        <input type="tel" v-model="form.code" maxlength="6" placeholder="请输入验证码">
        <view class="more">
          <text class="mo">获取验证码</text>
          <text class="mo" style="display: none">59秒后重试</text>
        </view>
      </view> -->
			<view class="info">
				<input :password='!isPassword' maxlength="26" v-model="form.password" placeholder="请输入密码">
				<view class="more">
					<text class="iconfont" :class="isPassword?'icon-eye-on':'icon-eye-off'"
						@click="isPassword = !isPassword"></text>
				</view>
			</view>
		</view>
		<!-- 按钮 -->
		<view class="btn-info">
			<view class="btn" style="opacity:1" @click="onRegister()">
				<text>绑定</text>
			</view>
		</view>
		<!-- 操作 -->
		<!-- <view class="operation">
      <text></text>
      <text @click="onLogin">已有账号?登录</text>
    </view> -->
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
			onLogin() {
				uni.redirectTo({
					url: '/pages/login/login'
				})
			},
			//切换绑定方式
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
			},
			/**
			 * 注册点击
			 */
			onRegister() {
				this.form.bindType = this.TabCur
				// 密码加密
				const user = {
					...this.form
				}
				user.password = md5(user.password)
				weChatApi.bindUser(user).then(res => {
					uni.showToast({
						title: res.msg
					})
					weChatApi.getLoginInfo().then(res => {
						uni.setStorageSync('loginUser', res.data)
						console.log(res.data)
					})
					
				})
				uni.switchTab({
						url: "/pages/home/home"
				})
			}
		},
		watch: {
			form: {
				handler(newValue, oldValue) {
					if (newValue.phone && newValue.code && newValue.password) {
						this.isRegister = true;
					} else {
						this.isRegister = false;
					}
				},
				deep: true
			}
		}
	}
</script>

<style scoped lang="scss">
	@import 'register.scss';
</style>

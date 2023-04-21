<template>
    <div style="text-align: center; margin: 0 20px">
      <div style="margin-top: 150px">
        <div style="font-size: 25px">登录</div>
        <div style="font-size: 14px; color: grey;margin-top: 10px;">
            在进入系统之前请先输入用户名和密码进行登录
        </div>
      </div>
    <el-form style="margin-top: 50px">
        <el-input type="text" v-model="form.username" placeholder="用户名/邮箱" :prefix-icon="User" />
        <el-input type="password" style="margin-top: 19px" v-model="form.password" placeholder="密码" :prefix-icon="Lock" show-password />
        <el-row style="margin-top: 5px">
            <el-col :span="12" style="text-align: left;">
                <el-checkbox label="记住我" v-model="form.remember"/>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <el-link @click="router.push('/forget')">忘记密码？</el-link>
            </el-col>
        </el-row>
    </el-form>
    <div style="margin-top: 20px;">
        <el-button type="success" style="width:270px" @click="login" plain>立即登录</el-button>
    </div>
    <el-divider>
        <span style="color: grey">没有账号</span>
    </el-divider>
    <div style="margin-top: 40px;">
        <el-button type="warning" style="width:270px" @click="router.push('/register')" plain>注册账号</el-button>
    </div>

</div>
</template>
<script setup>
import {User, Lock} from '@element-plus/icons-vue'
import {reactive} from 'vue'
import {ElMessage} from 'element-plus'
import {get, post} from "@/request/request";
import router from '@/router'
import {useStore} from "@/stores/store";

const store = useStore()

const form = reactive({
    username: '',
    password: '',
    remember: false
})

const login = () => {
    if(!form.username || !form.password) {
        ElMessage.warning('请填写用户名和密码！')
    } else {
        post('/api/auth/login', {
            username: form.username,
            password: form.password,
            remember: form.remember
        }, (message) => {
            ElMessage.success(message)
            get('/api/user/me',(message) => {
                store.auth.user = message
                router.push('/index')
            },() => {
                store.auth.user = null
            })
        })
    }
}
</script>
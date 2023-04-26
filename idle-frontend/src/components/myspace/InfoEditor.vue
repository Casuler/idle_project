<template>
<div class="content">
  <el-form :model="form"
           :rules="rules"
           ref="ruleForm"
           label-width="100px"
           class="demo-dynamic"
           >
      <el-form-item
          label="修改昵称"
          prop="nickname">
          <el-input v-model="form.nickname" :prefix-icon="User"/>
      </el-form-item>
      <el-form-item
              label="修改密码"
              prop="password">
          <el-input type="password" :prefix-icon="Lock" :maxlength="16" v-model="form.password" placeholder="新密码" show-password/>
      </el-form-item>
      <el-form-item>
          <div style="margin-top: 40px">
              <el-button @click="doUpgrade" style="width: 270px" type="danger" plain>立即修改</el-button>
          </div>
      </el-form-item>

  </el-form>
</div>
</template>

<script setup>
import {User, Lock} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {reactive, onMounted, ref} from 'vue'
import {get, post} from "@/request/request";
import router from "@/router";
const form = reactive({
    nickname: '',
    password:'',
    user:[]
})

const validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入昵称'))
    } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
        callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
    } else {
        callback()
    }
}

const rules = {
    nickname: [
        { validator: validateUsername, trigger: ['blur', 'change'] },
        { min: 2, max: 8, message: '昵称的长度必须在2-8个字符之间', trigger: ['blur', 'change'] },
    ],
    password: [
        { message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '密码的长度必须在6-16个字符之间', trigger: ['blur', 'change'] }
    ]
}

const doUpgrade = () => {
    if(form.nickname === '') form.nickname = form.user.nickname
    if(form.password === '') form.password = form.user.password
    post('/api/auth/upgrade-info',{
        nickname: form.nickname,
        password: form.password,
        email: form.user.email
    },message => {
        ElMessage.success(message)
        router.push('/index')
    })
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

onMounted(async ()=>{
    await getMe()
})
</script>

<style lang="less" scoped>
.content{
    width: 1140px;
    background-color: #fff;
}

.el-form{
  display: flex;
  flex-direction: column;
}

.el-form-item{
  margin-top: 20px;
  margin-left: 300px;
  width: 380px;
}

.label{
  width: 200px;
}
</style>
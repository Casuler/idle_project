<template>
<div class="content">
  <el-form :model="form"
           :rules="rules"
           ref="ruleForm"
           label-width="100px"
           >
      <el-form-item
          label="修改昵称"
          prop="nickname">
          <el-input v-model="form.user.nickname" placeholder="新昵称" :prefix-icon="User"/>
      </el-form-item>
      <el-form-item
              label="修改邮件地址"
              prop="email">
          <el-input type="email" :prefix-icon="Message" v-model="form.user.email" placeholder="电子邮件地址"/>
      </el-form-item>
      <el-form-item
              label="修改地址"
              prop="address">
          <el-input type="textarea" :prefix-icon="Location" :maxlength="50" v-model="form.user.address" placeholder="新地址"/>
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
import {User, Message, Location} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {reactive, onMounted, ref} from 'vue'
import {get, post} from "@/request/request";
import router from "@/router";
const form = reactive({
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
    ],
    email: [
        { message: '请输入邮件地址', trigger: 'blur' },
    ],
    address: [
        { message: '请输入地址', trigger:'blur'},
        { min: 6, max:50,message:'地址不能低于6个字符',trigger:['blur', 'change']}
    ]
}

const doUpgrade = () => {
    post('/api/auth/upgrade-info',{
        nickname: form.user.nickname,
        email: form.user.email,
        address: form.user.address,
        username: form.user.username
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

onMounted (async ()=>{
    await getMe()
})
</script>

<style lang="less" scoped>
.content{
    margin: 20px auto;
    background-color: #fff;
}

.el-form{
    margin-left: 25%;
    display: flex;
    flex-direction: column;
}

.el-form-item{
    width: 400px;
}

.label{
    width: 200px;
}
</style>
<script setup>
import {User, Message, EditPen} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {reactive, onMounted} from 'vue'
import {get, post} from "@/request/request";
import router from "@/router";
const form = reactive({
    nickname: '',
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
    nick: [
        { validator: validateUsername, trigger: ['blur', 'change'] },
        { min: 2, max: 8, message: '昵称的长度必须在2-8个字符之间', trigger: ['blur', 'change'] },
    ]
}

const doUpgrade = () => {
    post('/api/auth/upgrade-nickname',{
        nickname: form.nickname,
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

<template>
<div class="content">
  <el-form :model="form"
           :rules="rules"
           @validate="onValidate"
           ref="ruleForm"
           label-width="100px"
           class="demo-dynamic"
           >
      <el-form-item
          label="修改昵称"
          prop="nickname">
          <el-input v-model="form.nickname" :prefix-icon="User"/>
      </el-form-item>
      <el-form-item>
          <div style="margin-top: 40px">
              <el-button @click="doUpgrade" style="width: 270px" type="danger" plain>立即修改</el-button>
          </div>
      </el-form-item>

  </el-form>
</div>
</template>

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
  width: 300px;
}
</style>
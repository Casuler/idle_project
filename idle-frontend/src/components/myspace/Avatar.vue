<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage} from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import {get, post} from "@/request/request";

const form=reactive({
    user:[]
})

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

onMounted(async () => {
    await getMe()
})

const imageUrl = ref('')

const handleSuccess = (file) => {
    imageUrl.value = file.message
    console.log(file.message)
    console.log(imageUrl.value)
}

const handleBeforeUpload = (file) => {
    // 拿到文件后缀名
    const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
    console.log('文件上传类型', fileType)
    const isJpg = fileType === 'jpg'
    const isPng = fileType === 'png'
    const isJpeg = fileType === 'jpeg'
    const isGif = fileType === 'gif'
    const isLt10M = file.size / 1024 / 1024 < 3;
    // 根据后缀名判断文件类型
    if (!isJpg && !isPng && !isJpeg && !isGif ) {
        ElMessage.error('只能上传jpg/png/jpeg/gif格式的文件！')
        return false
    }
    // 可以限制图片的大小
    if (!isLt10M) {
        ElMessage.error('上传图片大小不能超过 3MB!');
    }
    return isJpg || isPng || isJpeg || isGif
}

const changeAvatar = () => {
    post('/api/auth/upgrade-avatar',{
        avatar: imageUrl.value,
        id: form.user.id
    }, message => {
        ElMessage.success(message)
    })
}
</script>

<template>
  <div class="content">
      <el-upload
          ref="upload"
          class="avatar-uploader"
          accept="image/jpg,image/jpeg,image/png"
          :with-credentials="true"
          action="http://localhost:8888/api/github/upload"
          :show-file-list="false"
          :on-success="handleSuccess"
          :before-upload="handleBeforeUpload"
          :limit="1">
          <img v-if="imageUrl" :src="imageUrl" class="avatar"/>
          <el-icon v-else class="avatar-uploader-icon"><Plus/></el-icon>
      </el-upload>
      <el-button type="success" style="width: 180px;margin-left: 10px;margin-top: 150px" @click="changeAvatar()" plain>确认修改</el-button>
  </div>
</template>

<style scoped>
.content{
    width: 1140px;
    background-color: #fff;
    display: flex;
}

.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>

<style lang="less">
.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>
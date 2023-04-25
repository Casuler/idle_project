<template>
  <div>
      <div class="publisher">
          <img :src="form.publisher.avatar" class="user-avatar" alt=""/>
          <span>{{ form.publisher.username }}</span>
          <el-button type="danger" plain
                     style="width: 70px;margin: 10px auto;translate: 400px"
                     @click="releaseGoods">
              发布商品
          </el-button>
      </div>
      <div class="product">
          <el-form style="margin-top: 20px">
              <el-input type="text" style="width: 270px" v-model="form.product_name" placeholder="商品名称"></el-input>
              <el-select v-model="value" style="width: 100px" placeholder="商品分类">
                  <el-option
                          v-for="item in category"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                  />
              </el-select>
              <el-input style="width: 100px" v-model="form.price" @input="handleInput" placeholder="￥商品价格"></el-input>
              <el-input type="text" disabled style="width: 200px" v-model="form.fileUrl"  placeholder="图片URL地址"></el-input>
              <el-input type="textarea" autosize style="width: 300px;" v-model="form.introduce"  placeholder="商品介绍"></el-input>
          </el-form>
          <el-upload
              ref="upload"
              accept="image/jpg,image/jpeg,image/png"
              :with-credentials="true"
              action="http://localhost:8888/api/github/upload"
              :before-upload="handleBeforeUpload"
              list-type="picture"
              class="upload-demo"
              :limit="1"
              :auto-upload="false"
              :on-exceed="handleExceed"
              :on-success="handleSuccess"
          >
              <template #trigger>
                  <el-button type="primary">选择图片</el-button>
              </template>
              <el-button
                  style="margin-left: 10px"
                  type="success"
                  @click="submitUpload"
              >上传图片
              </el-button>
              <template #tip>
                  <div style="color: red">
                      只能上传一张jpg/png文件，旧的文件将会被替换
                  </div>
              </template>
          </el-upload>
      </div>
  </div>
</template>

<script setup>
import {ElMessage, ElMessageBox} from 'element-plus'
import {useStore} from "@/stores/store";
import {get, post} from "@/request/request";
import router from "@/router";
import {ref,reactive,onMounted} from 'vue'
import { getCurrentInstance } from "vue";

const { proxy } = getCurrentInstance();
const upload = ref()
const form = reactive({
    publisher: [],
    product_name: '',
    price: '',
    fileUrl: '',
    introduce: ''
})

const handleExceed = (files, uploadFiles) => {
    console.log('限制上传', files, uploadFiles)
    ElMessageBox.alert('只能上传一个文件，如有需要请删除上一个文件再进行上传', '温馨提示', {
        confirmButtonText: '确定',
    })
}


const handleSuccess = (file) => {
    ElMessage.success('图片上传成功')
    console.log(file.message)
    form.fileUrl = file.message
}

const submitUpload = () => {
    console.log(upload.value)
    proxy.$refs.upload.submit()
}

// 文件上传前触发
const handleBeforeUpload = (file) => {
    // 拿到文件后缀名
    const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
    console.log('文件上传类型', fileType)
    const isJpg = fileType === 'jpg'
    const isPng = fileType === 'png'
    const isJpeg = fileType === 'jpeg'
    const isLt10M = file.size / 1024 / 1024 < 10;
    // 根据后缀名判断文件类型
    if (!isJpg && !isPng && !isJpeg ) {
        ElMessage.error('只能上传jpg/png/jpeg格式的文件！')
        return false
    }
    // 可以限制图片的大小
    if (!isLt10M) {
        ElMessage.error('上传图片大小不能超过 10MB!');
    }
    return isJpg || isPng || isJpeg
}

const value = ref('')
const category = [
    {
        value: '服饰',
        label: '服饰'
    },
    {
        value: '生活',
        label: '生活'
    },
    {
        value: '科技',
        label: '科技'
    }
]

const store = useStore()

const handleInput = () => {
    form.price = form.price.replace(/[^\d.]/g, '')
}
const getMe = () => {
    get('/api/user/me',(message)=> {
        form.publisher = message
    })
}

const releaseGoods = () => {
    post('/api/goods/set-goods',{
        publisher_id: form.publisher.id,
        product_name: form.product_name,
        price: form.price,
        category: value.value,
        introduce: form.introduce,
        picture: form.fileUrl
    },message =>{
        ElMessage.success(message)
        router.push('/index')
    })
}

onMounted(async () => {
    await getMe()
})

</script>

<style lang="less" scoped>
.publisher{
    width: 1140px;
    height: 80px;
    line-height: 80px;
    margin:10px auto;
    border-radius: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
    display: flex;
    align-items: center;

  .user-avatar {
    width: 60px;
    height: 60px;
    margin: 10px 10px;
    border-radius: 5px;
    cursor: pointer;
  }

  span{
    margin-left: 5px;
    cursor: pointer;
    font-size: 16px;
    font-weight: bold;
  }

}

.publisher:hover span{
  opacity: .8;
}

.product {
  width: 1140px;
  height: 650px;
  margin: 20px auto;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  background-color: #fff;
}
</style>
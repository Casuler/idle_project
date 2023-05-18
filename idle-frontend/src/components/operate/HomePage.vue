<template>
    <div class="main-content">
        <div style="width: 100%; height: 300px">
            <div class="banner">
                <el-image :src="top" style="height: 270px; width: 100%;opacity: 0.8"></el-image>
            </div>
            <div style="text-shadow: #626262 1px 1px 10px;
                   color:white; translate:30px -240px; ">
                <div style="font-size: 30px;font-weight: bold;">
                    闲置交易
                </div>
                <div style="font-size: 17px; width: 550px; margin-top: 10px">
                    大家可以在这里交易闲置物品
                </div>
            </div>
        </div>
        <div class="card-title">
            <el-button type="danger" plain @click="releaseDialog=true">
                <el-icon><Goods/></el-icon>
                发布闲置
            </el-button>
        </div>
        <div class="card">
            <div class="split-line"></div>
            <Category></Category>
        </div>
        <el-dialog
            v-model="releaseDialog"
            title="发布闲置商品"
            width="600px"
            append-to-body
        >
            <el-form ref="releaseRef" :rules="rules" :model="form" label-width="80px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品名称" prop="product_name">
                            <el-input type="text" style="width: 270px" v-model="form.product_name" placeholder="商品名称"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="商品分类" prop="category">
                            <el-select v-model="form.category" style="width: 80px" placeholder="商品分类">
                                <el-option
                                    v-for="item in category"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="商品介绍" prop="introduce">
                            <el-input type="textarea" autosize style="width: 300px;" v-model="form.introduce"  placeholder="商品介绍"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="商品价格" prop="price">
                            <el-input style="width: 100px" v-model="form.price" @input="handleInput" placeholder="￥商品价格"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="商品图片" prop="picture">
                            <el-upload
                                ref="upload"
                                accept="image/jpg,image/jpeg,image/png"
                                :with-credentials="true"
                                action="http://localhost:8888/api/github/upload"
                                :before-upload="handleBeforeUpload"
                                list-type="picture-card"
                                class="upload-demo"
                                :limit="1"
                                :auto-upload="false"
                                :on-exceed="handleExceed"
                                :on-success="handleSuccess"
                                :on-preview="handlePreview"
                            >
                                <template #trigger>
                                    <el-icon><Plus/></el-icon>
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
                            <el-dialog v-model="pictureDialog">
                                <img w-full :src="dialogImageUrl" alt="Preview Image"/>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <el-button @click="release(); releaseDialog=false" type="success" plain>发布商品</el-button>
                <el-button @click="cancel">取消</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import {Goods, Plus} from '@element-plus/icons-vue'
import Category from "@/components/Category.vue";
import {useStore} from "@/stores/store";
import {ref, reactive, onMounted} from 'vue'
import {get, post} from "@/request/request";
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCurrentInstance } from "vue";

const { proxy } = getCurrentInstance();

const top = 'https://itbaima.net/assets/resource-banner-27d717a1.jpg'
const pictureDialog = ref(false)
const releaseDialog = ref(false)
const dialogImageUrl = ref('')
const releaseRef = ref()
const upload = ref()

const form = reactive({
    publisher: [],
    product_name: '',
    price: '',
    picture: '',
    introduce: '',
    category: ''
})

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
    },
    {
        value: '图书',
        label: '图书'
    }
]

const rules = {
    product_name: [
        { required: true, message: '商品名不能为空', trigger: 'blur'},
        { min: 3, max: 25, message: '商品名称不能少于3个字符，且不超过25个字符', trigger: 'blur'}
    ],
    price: [
        {required: true, message: '价格不能为空', trigger: 'blur'}
    ],
    introduce: [
        {required: true, message: '商品介绍不能为空', trigger: 'blur'},
        {min: 5, max: 150, message: '商品介绍不能少于5个字符，且不超过150个字符', trigger: 'blur'}
    ],
    category: [
        {required: true, message: '请选择商品分类', trigger: 'blur'}
    ]
}

const handleInput = () => {
    form.price = form.price.replace(/[^\d.]/g, '')
}

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

const handleExceed = (files, uploadFiles) => {
    console.log('限制上传', files, uploadFiles)
    ElMessageBox.alert('只能上传一个文件，如有需要请删除上一个文件再进行上传', '温馨提示', {
        confirmButtonText: '确定',
    })
}

const handleSuccess = (file) => {
    ElMessage.success('图片上传成功')
    console.log(file.message)
    form.picture = file.message
}

const submitUpload = () => {
    console.log(upload.value)
    proxy.$refs.upload.submit()
}

const handlePreview = (file) => {
    dialogImageUrl.value = file.url
    pictureDialog.value = true
}

const release = () => {
    post('/api/goods/set-goods',{
        publisher_id: form.publisher.id,
        product_name: form.product_name,
        price: form.price,
        category: form.category,
        introduce: form.introduce,
        picture: form.picture
    },message =>{
        ElMessage.success(message)
    }, message => {
        ElMessage.warning(message)
    })
    reset()
}

function reset(){
    form.value = {
        publisher: [],
        product_name: '',
        price: '',
        fileUrl: '',
        introduce: ''
    };
    proxy.resetForm("releaseRef");
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.publisher = message
    })
}
function cancel() {
    releaseDialog.value = false
    reset();
}

onMounted(async () => {
    await getMe()
})
</script>

<style lang="less">
.main-content{
    display: flex;
    flex-direction: column;
    width: 100%;
    background-color: #f2f4f8;
}

.banner{
    height: 270px;
}

.card-title{
    margin-left: 50px;
    align-items: center;
    padding: 5px;
    display: flex;
    flex-direction: row;
}
.card{
    margin: 0 50px;
}

.split-line{
    margin-top: 5px;
    height: 1px;
    width: 100%;
    background-color: #dadada;
}

.el-input__wrapper{
    width: 80px;
}
</style>
<template>
    <div>
        <div class="publisher">
            <img :src="form.publisher.avatar" class="user-avatar" alt=""/>
            <span v-if="form.publisher.nickname!=null">{{ form.publisher.nickname }}</span>
            <span v-else>{{ form.publisher.username }}</span>
            <div class="buy" v-for="item in form.detail" :key="item.id" v-if="status == 1">
                <a class="price">￥{{ item.price }}</a>
                <el-button type="danger"
                           plain
                           style="width: 120px;margin-left: 10px;margin-top: -10px"
                           @click="goOrder()"
                >立即购买</el-button>
            </div>
            <div class="buy"  v-for="item in form.detail" :key="item.id" v-else>
                <a class="price">￥{{ item.price }}</a>
                <el-button type="danger"
                           plain
                           style="width: 120px;margin-left: 10px;margin-top: -10px"
                           @click="goOrder(form.detail.id)"
                           disabled
                >立即购买</el-button>
            </div>
        </div>
        <div class="product">
            <div class="product-container" v-for="item in form.detail" :key="item.id">
                <h2 v-if="route.query.status==='1'">{{ item.product_name }}</h2>
                <div v-else>
                    <h2 style="text-decoration: line-through">{{item.product_name}}</h2>
                    <span style="margin-left: 20px;color: red; font-size: 24px;font-weight: bold">该商品已下架</span>
                </div>

                <div class="split-line"></div>
                <div class="profile">
                    <span>{{item.introduce}}</span>
                </div>
                <div class="image-box">
                    <el-image :src="item.picture" alt=""/>
                </div>
                <div class="button" v-if="uid !== publisher_id">
                </div>
                <div class="button" v-else>
                    <span style="cursor:pointer;color: deepskyblue" @click="open=true">编辑商品</span>
                    <span style="cursor:pointer;margin-left: 5px;color: red" @click="updateStatus()" v-if="route.query.status==='1'">下架商品</span>
                </div>
            </div>
            <el-dialog title="修改商品信息" v-model="open" width="600px" append-to-body>
                <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="商品名称" prop="product_name">
                                <el-input v-model="form.product_name" placeholder="请输入商品名称" maxlength="30" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="商品价格" prop="price">
                                <el-input style="width: 100px" v-model="form.price" @input="handleInput" placeholder="￥商品价格"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="商品分类" prop="category">
                                <el-select v-model="form.category" style="width: 100px" placeholder="商品分类">
                                    <el-option
                                        v-for="item in category"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="商品介绍" prop="introduce">
                                <el-input type="textarea" autosize style="width: 300px;" v-model="form.introduce"  placeholder="商品介绍"></el-input>
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
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="updateGoods();open=false">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script setup>
import {reactive, onMounted, ref} from 'vue'
import {useStore} from "@/stores/store";
import {useRoute} from 'vue-router'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'
import {post} from "@/request/request";
import router from "@/router";
import { getCurrentInstance } from "vue";

const { proxy } = getCurrentInstance();
const upload = ref()
const store = useStore()
const route = useRoute()
const id = route.query.id
const uid = route.query.uid
const publisher_id = route.query.publisher_id
const status = route.query.status

const open = ref(false)
const formRef = ref()
const form = reactive({
    detail: [],
    publisher: [],
    price: '',
    product_name: '',
    category: '',
    introduce: '',
    picture: ''
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
    }
]


const rules = {
    product_name: [
        { required: true,  trigger: 'blur'},
        { min: 3, max: 25, message: '商品名称不能少于3个字符，且不超过25个字符', trigger: 'blur'}
    ],
    price: [
        {required: true, trigger: 'blur'}
    ],
    introduce: [
        {required: true, trigger: 'blur'},
        {min: 5, max: 150, message: '商品介绍不能少于5个字符，且不超过150个字符', trigger: 'blur'}
    ],
    category: [
        {required: true, message: '请选择商品分类', trigger: 'blur'}
    ]
}

const handleInput = () => {
    form.price = form.price.replace(/[^\d.]/g, '')
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

function reset() {
    form.value = {
        detail: [],
        publisher: [],
        price: '',
        product_name: '',
        category: '',
        introduce: '',
        picture: ''
    };
    proxy.resetForm("formRef");
}

function cancel() {
    open.value = false
    reset();
}

const updateGoods = () => {
    post('/api/goods/update-goods',{
        id: id,
        product_name: form.product_name,
        price: form.price,
        category: form.category,
        introduce: form.introduce,
        picture: form.picture
    }, message => {
        ElMessage.success(message)
    })
    reset()
}

const getGoodsById = () => {
    post('/api/goods/find-goods', {
        id: id
    }, message => {
        form.detail = message
        form.product_name = message[0].product_name
        form.price = message[0].price
        form.category = message[0].category
        form.introduce = message[0].introduce
        form.picture = message[0].picture
    })
}

const getPublisher = () => {
    post('/api/goods/get-publisher', {
        publisher_id: publisher_id
    },message => {
        form.publisher = message
    })
}

const goOrder = () => {
    router.push({
        path:`/order/${id}`,
        query:{
            id: id,
            seller: form.publisher.nickname
        }
    })
}

const updateStatus = () =>{
    post('/api/goods/update-status',{
        id: id,
        status: status
    },message =>{
        ElMessage.success(message)
        router.push('/index')
    })
}

onMounted(async () => {
    await getGoodsById()
    await getPublisher()
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
    align-items: center;
    display: flex;
}

.user-avatar {
    width: 60px;
    height: 60px;
    margin: 10px 10px;
    border-radius: 5px;
    cursor: pointer;
}

.publisher span{
    margin-left: 5px;
    cursor: pointer;
    font-size: 16px;
    font-weight: bold;
}

.publisher:hover span{
    opacity: .8;
}

.product {
    width: 1140px;
    height: auto;
    display: flex;
    flex-direction: column;
    margin: 20px auto;
    border-radius: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
}

.product-container {
    width: 1140px;
    min-height: 650px;
    display: flex;
    flex-direction: column;
}

.product-container h2{
    margin-left: 20px;
}

.image-box{
    height: 500px;
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    .el-image{
        margin: 0 auto;
        align-items: center;
        width: 450px;
        height: 500px;
    }
}

.profile{
    margin-left: 20px;
    font-size: 18px;
}

.button{
    margin-left: 20px;
    margin-top: 20px;

}

.buy{
    width: 300px;
    margin: 0 auto;
    translate: 420px 0;
    align-items: center;
}

.price{
    font-size: 26px;
    font-weight: 700;
    color: #ff4400;
    line-height: 80px;
}

.split-line{
    margin-top: 5px;
    height: 1px;
    width: 100%;
    background-color: #dadada;
}
</style>
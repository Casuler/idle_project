<template>
    <div>
        <div class="publisher">
            <img :src="form.publisher.avatar" class="user-avatar" alt=""/>
            <span v-if="form.publisher.nickname!=null">{{ form.publisher.nickname }}</span>
            <span v-else>{{ form.publisher.username }}</span>
            <div class="buy" v-for="item in form.detail" :key="item.id" v-if="status = 1">
                <a class="price">￥{{ item.price }}</a>
                <el-button type="danger"
                           plain
                           style="width: 70px;margin-left: 10px;margin-top: -10px"
                           @click="goOrder(form.detail.id)"
                >立即购买</el-button>
            </div>
            <div class="buy"  v-for="item in form.detail" :key="item.id" v-else>
                <a class="price">￥{{ item.price }}</a>
                <el-button type="danger"
                           plain
                           style="width: 70px;margin-left: 10px;margin-top: -10px"
                           @click="goOrder(form.detail.id)"
                           disabled
                >立即购买</el-button>
            </div>
        </div>
        <el-scrollbar wrap-style="overflow-x:hidden;" style="width: 100%;" height="720px">
            <div class="product">
                <div class="product-container" v-for="item in form.detail" :key="item.id">
                    <h2 v-if="route.query.status==1">{{ item.productName }}</h2>
                    <div v-else>
                        <h2 style="text-decoration: line-through">{{item.productName}}</h2>
                        <span style="margin-left: 20px;color: red; font-size: 24px;font-weight: bold">该商品已下架</span>
                    </div>

                    <div class="split-line"></div>
                    <div class="profile">
                        <span>{{item.introduce}}</span>
                    </div>
                    <div class="image-box">
                        <img :src="item.picture" alt=""/>
                    </div>
                </div>
                <div class="button" v-if="uid !== publisherId">
                </div>
                <div class="button" v-else>
                    <span style="cursor:pointer;color: deepskyblue">编辑商品</span>
                    <span style="cursor:pointer;margin-left: 5px;color: red" @click="updateStatus()" v-if="route.query.status==1">下架商品</span>
                </div>
            </div>
        </el-scrollbar>
    </div>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {useStore} from "@/stores/store";
import {useRoute} from 'vue-router'
import {ElMessage} from 'element-plus'
import {post} from "@/request/request";
import router from "@/router";

const store = useStore()
const route = useRoute()
const id = route.query.id
const uid = route.query.uid
const publisherId = route.query.publisherId
const status = route.query.status

const form = reactive({
    detail: [],
    publisher: [],
})
const getGoodsById = () => {
    post('/api/goods/find-goods', {
        id: id
    }, message => {
        form.detail = message
    })
}

const getPublisher = () => {
    post('/api/goods/get-publisher', {
        publisherId: publisherId
    },message => {
        form.publisher = message
    })
}

const goOrder = () => {
    router.push({
        path:`/order/${id}`,
        query:{
            id: id
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

<style scoped>
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
    height: 650px;
    margin: 20px auto;
    border-radius: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
}

.product-container {
    width: 1140px;
    min-height: 650px;
}

.product-container h2{
    margin-left: 20px;
}

.image-box{
    width: 100%;
    height: 100%;
    margin-top: 10px;
}

.image-box img{
    margin-left: 25%;
    width: 450px;
    height: 500px;
}

.profile{
    margin-left: 20px;
    font-size: 18px;
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

.button{
    float: right;
}
</style>
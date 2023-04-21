<template>
    <div>
        <div class="publisher">
            <img :src="form.publisher.avatar" class="user-avatar" alt=""/>
            <span>{{ form.publisher.username }}</span>
            <div class="buy" v-for="item in form.detail" :key="item.id">
                <a class="price">￥{{ item.price }}</a>
                <el-button type="danger" plain style="width: 70px;margin-left: 10px;margin-top: -10px">立即购买</el-button>
            </div>
        </div>
        <el-scrollbar wrap-style="overflow-x:hidden;" style="width: 100%;" height="720px">
            <div class="product">
                <div class="product-container" v-for="item in form.detail" :key="item.id">
                    <h2>{{ item.productName }}</h2>
                    <div class="split-line"></div>
                    <div class="profile">
                        <span>{{item.introduce}}</span>
                    </div>
                    <div class="image-box">
                        <img :src="item.picture" alt=""/>
                    </div>
                </div>
            </div>
            <div class="comment">
                <div class="comment-title">
                    <h2>评论区</h2>
                </div>
                <div class="split-line"></div>
                <div class="comment-box">
                    <div class="user-box">
                        <img
                            :src="
            userInfo.avatar ||
            'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif' +
              '?imageView2/1/w/80/h/80'
          "
                            class="user-avatar"
                            alt=""
                        />
                        <span>{{userInfo.username}}</span>
                    </div>
                    <div class="comment-container">
                        <span>非常好东西，爱来自中国</span>
                    </div>
                </div>
            </div>
        </el-scrollbar>
    </div>
</template>

<script setup>
import {reactive, computed, onMounted} from 'vue'
import {useStore} from "@/stores/store";
import {useRoute} from 'vue-router'
import {post} from "@/request/request";

const store = useStore()
const userInfo = computed(() => store.auth.user)
const route = useRoute()
const id = route.query.id
const publisherId = route.query.publisherId

const form = reactive({
    detail: [],
    publisher: []
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
    height: 400px;
    margin-top: 20px;
}

.image-box img{
    margin-left: 25%;
    width: 460px;
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

.comment{
    width: 1140px;
    height: 100%;
    margin: 0 auto;
    border-radius: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
}

.comment-title{
    margin-left: 20px;
}

.comment-box{
    display: flex;
    margin-left: 20px;
}

.user-box{
    display: flex;
    width: 180px;
    height: 80px;
    border-right: 1px solid #dadada;
}

.user-box span{
    height: 80px;
    line-height: 80px;
    text-align: center;
}

.comment-container{
    margin-left: 20px;
    height: 80px;
    line-height: 80px;
}
</style>
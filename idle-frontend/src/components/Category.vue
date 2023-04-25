<template>
    <el-button @click="getGoods">全部</el-button>
    <el-button @click="getCategory(form.category='服饰')">服饰</el-button>
    <el-button @click="getCategory(form.category='生活')">生活</el-button>
    <el-button @click="getCategory(form.category='科技')">科技</el-button>
    <div class="Search">
        <el-input type="text" v-model="form.search" placeholder="Search...">
            <template #suffix>
                <el-icon style="cursor: pointer" @click="searchGoods"><Search /></el-icon>
            </template>
        </el-input>
    </div>
        <div class="good">
            <header class="good-header">{{ form.category }}</header>
            <div class="good-box">
                <div class="good-item" v-for="item in form.goodsList" :key="item.id"
                     @click="goDetail(item)">
                    <img :src="item.picture" alt=""/>
                    <div class="good-desc">
                        <div class="title">{{item.product_name}}</div>
                        <div class="price">￥ {{item.price.toFixed(2)}}</div>
                        <div style="color: deepskyblue" v-if="item.status">上架中</div>
                        <div style="color: red" v-else>已下架</div>
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import router from "@/router";
import {get, post} from "@/request/request";
import {ElMessage} from 'element-plus'
import {Search} from '@element-plus/icons-vue'

const form = reactive({
    goodsList:[],
    category: [],
    user:[],
    search: ''
})
const getGoods = () => {
    get('/api/goods/get-goods',(message) => {
        form.goodsList = message
        form.category = '全部'
    })
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

onMounted(async () =>{
    await getGoods()
    await getMe()
})

const getCategory = () => {
    post('/api/goods/get-category-good', {
        category: form.category
    },message => {
        form.goodsList = message
    })
}

const goDetail = (item) => {
    router.push({
        path:`/product/${item.id}`,
        query: {
            id: item.id,
            publisher_id: item.publisher_id,
            uid: form.user.id,
            status: item.status
        }
    })
}

const searchGoods = () => {
    post('/api/goods/get-good-name',{
        search: form.search
    },message =>{
        ElMessage.success("搜索成功")
        form.goodsList = message
        form.category = "搜索'" + form.search + "'"
    })
}
</script>

<style lang="less" scoped>
.Search{
    width: 220px;
    margin-top: 5px;
    height: 40px;
    .el-input{
        width: 200px;
        height: 40px;
        padding: 0;
        border: none;
        outline: none;
        font-size: 18px;
    }
}
.good {
    .good-header {
        background: #f9f9f9;
        height: 50px;
        line-height: 50px;
        text-align: center;
        color: #000000;
        font-size: 16px;
        font-weight: 500;
    }
    .good-box {
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
        .good-item {
            box-sizing: border-box;
            width: 50%;
            border-bottom: 1px solid #dadada;
            padding: 10px 10px;
            text-align: center;
            img {
                display: block;
                width: 300px;
                height: 300px;
                margin: 0 auto;
            }
            .good-desc {
                text-align: center;
                font-size: 14px;
                padding: 10px 0;
                .title {
                    font-size: 20px;
                    font-weight: bold;
                    color: #222333;
                }
                .price {
                    color: #f56c6c;
                  font-size: 16px;
                }
            }
            &:nth-child(2n+1) {
                border-right: 1px solid #dadada;
            }
        }
    }
}
</style>
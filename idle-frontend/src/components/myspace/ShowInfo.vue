<template>
<div class="content">
    <el-image :src="form.user.avatar" class="image" alt=""/>
    <el-descriptions :column="1" border>
        <el-descriptions-item label="昵称" v-model="form.user.nickname">
            <p class="message">{{form.user.nickname}}</p>
        </el-descriptions-item>
        <el-descriptions-item label="用户名" v-model="form.user.username">
            <p class="message">{{form.user.username}}</p>
        </el-descriptions-item>
        <el-descriptions-item label="邮箱" v-model="form.user.email">
            <p class="message">{{form.user.email}}</p>
        </el-descriptions-item>
        <el-descriptions-item label="地址" v-model="form.user.address">
            <p class="message">{{form.user.address}}</p>
        </el-descriptions-item>
    </el-descriptions>
</div>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {get} from "@/request/request";

const form = reactive({
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
</script>

<style scoped>
.content{
    margin: 20px auto;
    width: 980px;
    background-color: #fff;
}
.message{
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.image{
    width: 80px;
    height: 80px;
    border: 1px solid #dadada;
    border-radius: 48px;
}
</style>
<template>
    <div style="height: 100vh;">
        <el-container style="height: 100%">
            <el-header class="header">
                <div class="header-title">
                    <img src="@/assets/images/favicon.png" class="logo" @click="router.push('/index')">
                    <span style="align-items: center">Idle—大学生自己的二手交易平台</span>
                </div>
                <div class="header-prop">
                    <a @click="router.push('/index')">首页</a>
                    <a @click="warningMessage">分享交流</a>
                </div>
                <div class="header-user">
                    <User></User>
                </div>
            </el-header>
                <el-main style="height: 100%; width: 100%;">
                    <router-view v-slot="{ Component }">
                        <transition name="el-fade-in-linear" mode="out-in">
                            <component :is="Component"/>
                        </transition>
                    </router-view>
                    <div class="time">{{ timer }}</div>
                </el-main>
        </el-container>
    </div>
</template>

<script setup>
import {User,warningMessage} from "@/components/index"
import router from '@/router'
import {format} from 'date-fns'
import {onMounted,ref} from 'vue'


let setTimer = ''
let timer = ref('')

const currentTime = () => {
    timer.value = format(new Date(),'yyyy/MM/dd HH:mm:ss')
}

onMounted(async () => {
    currentTime()
    setTimer = setInterval(() => {
        currentTime()
    },1000)
})

</script>
<style lang="less" scoped>
.header{
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 70px;
    background-color: white;
    color: #000000;
    border-bottom: 1px solid #e5e5e5;
    .header-title{
        display: flex;
        align-items: center;
    }
    .header-title img{
        width: 100px;
        height: 60px;
    }
    .logo {
        cursor: pointer;
    }
    .header-prop{
        display: flex;
        align-items: center;
        font: 16px Arial,Helvetica, sans-serif;
    }
    .header-prop a{
        text-align: center;
        width: 64px;
        text-decoration: none;
        color: #000000;
        font-size: 16px;
        font-weight: normal;
        opacity: 0.9;
        cursor: pointer;
    }

    .header-prop a:hover{
        color: #f56c6c;
    }

    .header-user{
        width: 120px;
        height: 70px;
    }
}
.time{
    margin-left: 50px;
}
</style>
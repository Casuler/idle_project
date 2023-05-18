<template>
    <el-container>
        <el-aside width="250px" style="margin-left: 10%">
            <el-menu :default-openeds="['1']">
                <el-sub-menu index="1">
                    <template #title>
                        <el-icon><EditPen/></el-icon>
                        <span>信息管理</span>
                    </template>
                    <el-menu-item-group>
                        <router-link class="alink" to="/myspace/showinfo">
                            <el-menu-item index="1-1">
                                <el-icon><User/></el-icon>
                                基本信息
                            </el-menu-item>
                        </router-link>

                        <router-link class="alink" to="/myspace/infoeditor">
                            <el-menu-item index="1-2">
                                <el-icon><Edit/></el-icon>
                                信息修改
                            </el-menu-item>
                        </router-link>

                        <router-link class="alink" to="/myspace/avatar">
                            <el-menu-item index="1-3">
                                <el-icon><Avatar/></el-icon>
                                头像修改
                            </el-menu-item>
                        </router-link>
                    </el-menu-item-group>
                </el-sub-menu>
                <el-sub-menu index="2">
                    <template #title>
                        <el-icon><Document/></el-icon>
                        商品管理
                    </template>
                    <el-menu-item-group>
                        <div class="alink" @click="goMyOrder()">
                            <el-menu-item index="2-1">
                                <el-icon><TakeawayBox/></el-icon>
                                我的订单
                            </el-menu-item>
                        </div>
                        <div class="alink" @click="goMyGood()">
                            <el-menu-item index="2-2">
                                <el-icon><Goods/></el-icon>
                                我的商品
                            </el-menu-item>
                        </div>
                    </el-menu-item-group>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <el-container style="margin-left: 50px;margin-right: 10%;background-color:white">
            <el-main style="overflow-x: hidden">
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup>
import {EditPen,User,Edit,Avatar,Document,TakeawayBox,Goods} from "@element-plus/icons-vue"
import router from "@/router";
import {reactive, onMounted} from 'vue'
import {get} from "@/request/request";

const form = reactive({
    user:[]
})

const goMyOrder = () => {
    router.push({
        path:  `/myspace/myorder/${form.user.id}`,
        query: {
            nickname: form.user.nickname
        }
    })
}
const goMyGood = () => {
    router.push({
        path:  `/myspace/mygood/${form.user.id}`,
        query: {
            publisher_id: form.user.id
        }
    })
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

onMounted(async () => {
    await getMe()
})
</script>

<style lang="less">
.el-header{
    background-color: #e5efe2;
    color: black;
    line-height: 60px;
}
.el-aside{
    color: black;
}
.router-link-active{
    text-decoration: none;
}
.alink{
    text-decoration: none;
}

</style>
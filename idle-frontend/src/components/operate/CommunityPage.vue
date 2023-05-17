<template>
    <div class="main-content">
        <el-scrollbar wrap-style="overflow-x:hidden;" style="width: 100%;">
            <div style="margin: 0 50px 20px;min-width: 800px; height: 300px">
                <div class="banner">
                    <el-image :src="top" style="height: 270px; width: 100%;opacity: 0.8"></el-image>
                </div>
                <div style="text-shadow: rgb(98,98,98) 1px 1px 10px;
                   color:white; translate:30px -240px; ">
                    <div style="font-size: 30px;font-weight: bold;">
                        分享交流
                    </div>
                    <div style="font-size: 17px; width: 550px; margin-top: 10px">
                        大家可以在这里分享交流处理闲置物品的经验
                    </div>
                </div>
            </div>
            <div class="card">
                <el-button type="danger" plain>
                    <el-icon><Edit/></el-icon>
                    发帖
                </el-button>
                <div class="split-line"></div>
                <Community></Community>
            </div>
        </el-scrollbar>
    </div>
</template>

<script setup>
import {Edit} from '@element-plus/icons-vue'
import Community from "@/components/Community.vue";

const top = 'https://itbaima.net/assets/resource-banner-27d717a1.jpg'

</script>

<style scoped>
.main-content{
    display: flex;
    width: 100%;
    height: calc(100vh - 61px);
    background-color: #f2f4f8;
}

.banner{
    height: 270px;
}

.card{
    border-radius: 8px;
    margin: 0 50px;
}

.split-line{
    margin-top: 5px;
    height: 1px;
    width: 100%;
    background-color: #dadada;
    margin-bottom: 5px;
}
</style>
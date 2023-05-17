<template>
    <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
        <div style="flex: 1">
            <el-image style="width: 100%; height: 100%" fit="cover" src="src/assets/images/wallpaper/2.jpg" />
        </div>
        <div class="welcome-title">
            <div style="font-size: 30px; font-weight:bold">欢迎来到Idle校园闲鱼二手交易网站</div>
            <div style="margin-top: 10px">在这里你可以交易闲置物品，与其他用户一起分享交流处理闲置物品的心得。</div>
        </div>
        <div class="welcome-form">
            <router-view v-slot="{ Component }">
                <transition name="el-fade-in-linear" mode="out-in">
                <component :is="Component" style="height: 100%"/>
                </transition>
            </router-view>
        </div>
    </div>
</template>

<style scoped>
.welcome-title {
    position: absolute;
    bottom: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
.welcome-form {
    width: 400px;
    background-color: white;
    z-index: 1;
}
</style>
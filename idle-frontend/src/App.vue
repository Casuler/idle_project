<template>
    <router-view></router-view>
</template>

<script setup>
import { get } from '@/request/request'
import { useStore } from '@/stores/store'
import router from '@/router'

const store = useStore()

if(store.auth.user == null){
    get('/api/user/me',(message) => {
        store.auth.user = message
        router.push('/index')
    },() => {
        store.auth.user = null
    })
}
</script>
<style lang="less">
* {
    overflow: hidden;
}
::-webkit-scrollbar {
    width: 0 !important;
}

::-webkit-scrollbar {
    width: 0 !important;
    height: 0;

}
body{
    height: 100vh;
    background-color: #f2f4f8;
}
</style>


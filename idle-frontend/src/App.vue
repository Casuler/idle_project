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
body{
    overflow: hidden;
    background-color: #f2f4f8;
}
</style>


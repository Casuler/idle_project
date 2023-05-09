<template>
    <div class="user-container">
        <div class="user-wrapper">
                <div class="user-name" v-if="form.user.nickname!=null">{{ form.user.nickname }}</div>
                <div class="user-name" v-else>{{form.user.username}}</div>
            <el-dropdown>
                <el-avatar :src="form.user.avatar" :size="50" alt=""/>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="goMyspace()" :icon="User">
                            个人中心
                        </el-dropdown-item>
                        <el-dropdown-item @click="warningMessage" :icon="ChatDotRound">
                            消息
                        </el-dropdown-item>
                        <el-dropdown-item @click="logout()" :icon="SwitchButton">
                            退出登录
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import { useStore } from '@/stores/store'
import { useRouter} from 'vue-router'
import {get} from "@/request/request";
import {ElMessage} from 'element-plus'
import {ChatDotRound, SwitchButton, User} from '@element-plus/icons-vue'
import {warningMessage} from "@/components/index";

const router = useRouter()
const store = useStore()

const form = reactive({
    user: []
})

const logout = () => {
    get('/api/auth/logout', (message) => {
        ElMessage.success(message)
        store.auth.user = null
        router.push('/')
    })
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

const goMyspace = () => {
    router.push("/myspace")
}
onMounted(async () => {
   await getMe()
})
</script>

<style lang="less" scoped>
.user-container {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 10px;
    transition: background 0.3s;

    .user-wrapper {
        display: flex;
        align-items: center;
        .el-avatar {
            cursor: pointer;
        }
        .user-name {
            padding-right: 10px;
            white-space: nowrap;
            font-size: 16px;
            -webkit-user-select:none;
            -moz-user-select:none;
            -ms-user-select:none;
            user-select:none;
        }
    }
}
</style>
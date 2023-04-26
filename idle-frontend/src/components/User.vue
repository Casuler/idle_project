<template>
    <div class="user-container">
        <el-dropdown trigger="click">
            <div class="user-wrapper">
                <div class="user-name" v-if="form.user.nickname!=null">{{ form.user.nickname }}</div>
                <div class="user-name" v-else>{{form.user.username}}</div>
                <img :src="form.user.avatar" class="user-avatar" alt=""/>
            </div>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item @click="goMyspace()">
                        <el-icon><User/></el-icon>
                        个人中心
                    </el-dropdown-item>
                    <el-dropdown-item @click="warningMessage">
                        <el-icon><ChatDotRound/></el-icon>
                        消息
                    </el-dropdown-item>
                    <el-dropdown-item @click="logout()">
                        <el-icon><SwitchButton/></el-icon>
                        退出登录
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
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

<style scoped>
.user-container {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 10px;
    transition: background 0.3s;

    &:hover {
        background: rgba(0, 0, 0, 0.025);
    }

    .user-wrapper {
        display: flex;
        align-items: center;

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 5px;
            margin-right: 40px;
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
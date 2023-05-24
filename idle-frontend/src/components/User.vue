<template>
    <div class="user-container">
        <div class="user-wrapper">
            <div class="user-name" v-if="form.user.nickname!=null">{{ form.user.nickname }}</div>
            <div class="user-name" v-else>{{form.user.username}}</div>
            <div class="user-email">{{form.user.email}}</div>
        </div>
        <el-dropdown>
            <el-avatar :src="form.user.avatar" :size="50" alt=""/>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item @click="goMyspace()" :icon="User">
                        个人中心
                    </el-dropdown-item>
                    <!--                        <el-dropdown-item @click="warningMessage" :icon="ChatDotRound">-->
                    <!--                            消息-->
                    <!--                        </el-dropdown-item>-->
                    <el-dropdown-item @click="logout()" :icon="SwitchButton">
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
import {SwitchButton, User} from '@element-plus/icons-vue'

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
    transition: background 0.3s;

    .user-wrapper {
        display: flex;
        flex-direction: column;
        margin: 10px 10px 0 0;
        line-height: 20px;
        .user-name {
            text-align: right;
            font-size: 18px;
        }
        .user-email {
            color: grey;
            font-size: 14px;
        }
    }

    .el-avatar {
        cursor: pointer;
        outline: none;
    }
}
</style>
import {ElMessage} from 'element-plus'

const warningMessage = () => {
    ElMessage.error('该功能还未上线，敬请期待')
}


export {default as User} from '@/components/User.vue'
export {warningMessage}
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'

import {resetForm} from "@/utils/idle";

import {resetForm} from "@/utils/idle";

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8888'

app.config.globalProperties.resetForm = resetForm

app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')

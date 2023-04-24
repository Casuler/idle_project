import { createRouter, createWebHistory } from 'vue-router'
import {useStore} from "@/stores/store";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/WelcomeView.vue'),
      children: [
        {
          path: '',
          name: 'welcome-login',
          component: ()=> import('@/components/welcome/LoginPage.vue')
        },
        {
          path: '/register',
          name: 'welcome-register',
          component : () => import('@/components/welcome/RegisterPage.vue')
        },
        {
          path: '/forget',
          name: 'welcome-forget',
          component : () => import('@/components/welcome/ForgetPage.vue')
        }
      ]
    },
    {
      path: '/index',
      name: 'index',
      component: () => import('@/views/indexView.vue'),
      children:[
        {
          path: '',
          name: 'operate-home',
          component : () => import('@/components/operate/HomePage.vue')
        },
        {
          path: 'community',
          name: 'operate-community',
          component : () => import('@/components/operate/CommunityPage.vue')
        },
        {
          path: 'product/:id',
          name: 'operate-product',
          component : () => import('@/components/operate/ProductPage.vue'),
          props: true
        },
        {
          path: 'release',
          name: 'operate-release',
          component : () => import('@/components/operate/ReleasePage.vue')
        },
        {
          path: 'order/:id',
          name: 'operate-order',
          component: () => import('@/components/operate/OrderPage.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const store = useStore()
  if(store.auth.user != null && to.name.startsWith('welcome-')) {
    next('/index')
  } else if(store.auth.user == null && to.fullPath.startsWith('/index')) {
    next('/')
  }  else if(to.matched.length === 0){
    next('/index')
  } else {
    next()
  }
})
export default router

import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Welcome',
            redirect: "/login",
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '/login',
                    name: 'Welcome-Login',
                    component: () => import('@/components/Welcome/LoginPage.vue')
                },
                {
                    path: '/register',
                    name: 'Welcome-Register',
                    component: () => import('@/components/Welcome/RegisterPage.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue')
        }
    ]
})

export default router

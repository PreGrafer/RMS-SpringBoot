import {createRouter, createWebHistory} from 'vue-router'
import {useStore} from "@/stores";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Welcome',
            redirect: '/login',
            component: () => import('@/views/WelcomeView.vue'),
            meta: {requiresAuth: false},
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
                },
                {
                    path: '/forgot',
                    name: 'Welcome-Forgot',
                    component: () => import('@/components/Welcome/ForgotPage.vue')
                }
            ]
        },
        {
            path: '/user',
            name: 'User-Main',
            redirect: '/user/account',
            component: () => import('@/views/UserView.vue'),
            meta: {requiresAuth: true, role: "user"},
            children: [
                {
                    path: 'account',
                    name: 'User-Account',
                    component: () => import('@/components/All/AccountView.vue')
                },
                {
                    path: 'use-house',
                    name: 'Use-House',
                    component: () => import('@/components/User/UseHouseView.vue')
                },
                {
                    path: 'house-app',
                    name: 'House-Application',
                    component: () => import('@/components/User/HouseApplicationView.vue')
                },
                {
                    path: 'house-app/:house',
                    name: 'Single-House',
                    component: () => import('@/components/User/HouseAppInfoView.vue')
                }
            ]
        },
        {
            path: '/worker',
            name: 'Worker-Main',
            redirect: '/worker/account',
            component: () => import('@/views/WorkView.vue'),
            meta: {requiresAuth: true, role: "worker"},
            children: [
                {
                    path: 'account',
                    name: 'Worker-Account',
                    component: () => import('@/components/All/AccountView.vue')
                },
                {
                    path: 'houses',
                    name: 'Worker-Houses-Manage',
                    component: () => import('@/components/All/HousesView.vue')
                },
                {
                    path: 'houses/:house_id',
                    name: 'Worker-House-Manage',
                    component: () => import('@/components/All/HouseManageView.vue')
                },
                {
                    path: 'register-manage',
                    name: 'User-Register-Manage',
                    component: () => import('@/components/Worker/UserRegisterListView.vue')
                },
                {
                    path: 'use-house-manage',
                    name: 'User-House-Manage',
                    component: () => import('@/components/Worker/UseHouseMangeView.vue')
                }
            ]
        },
        {
            path: '/admin',
            name: 'Admin-Main',
            redirect: '/admin/account',
            component: () => import('@/views/AdminView.vue'),
            meta: {requiresAuth: true, role: "admin"},
            children: [
                {
                    path: 'account',
                    name: 'Admin-Account',
                    component: () => import('@/components/All/AccountView.vue')
                },
                {
                    path: 'accounts',
                    name: 'Accounts-Manage',
                    component: () => import('@/components/Admin/AccountsView.vue')
                },
                {
                    path: 'accounts/:manage',
                    name: 'Admin-Account-Manage',
                    component: () => import('@/components/Admin/AccountManageView.vue')
                },
                {
                    path: 'houses',
                    name: 'Admin-Houses-Manage',
                    component: () => import('@/components/All/HousesView.vue')
                },
                {
                    path: 'houses/:house_id',
                    name: 'Admin-House-Manage',
                    component: () => import('@/components/All/HouseManageView.vue')
                },
                {
                    path: 'register-manage',
                    name: 'Register-Manage',
                    component: () => import('@/components/Admin/RegisterListView.vue')
                }
            ]
        }
    ]
})
export default router

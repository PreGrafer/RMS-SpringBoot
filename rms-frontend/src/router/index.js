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
            component: () => import('@/views/UserView.vue'),
            meta: {requiresAuth: true, roles: ["user"]},
            children: [
                {
                    path: ':username',
                    name: 'User-Main',
                    redirect: to => {
                        return {path: `/user/${to.params.username}/account`};
                    },
                    children: [
                        {
                            path: 'account',
                            name: 'User-Account',
                            component: () => import('@/components/User/AccountView.vue')
                        },
                        {
                            path: 'use-house',
                            name: 'Use-House',
                            component: () => import('@/components/User/UseHouseView.vue')
                        },
                        {
                            path: 'past-house',
                            name: 'Past-House',
                            component: () => import('@/components/User/PastHouseView.vue')
                        }
                    ]
                }
            ]
        },
        {
            path: '/worker',
            component: () => import('@/views/WorkView.vue'),
            meta: {requiresAuth: true, roles: ["worker"]},
            children: [
                {
                    path: ':username',
                    name: 'Worker-Main',
                    redirect: to => {
                        return {path: `/worker/${to.params.username}/account`};
                    },
                    children: [
                        {
                            path: 'account',
                            name: 'Worker-Account',
                            component: () => import('@/components/Worker/AccountView.vue')
                        },
                        {
                            path: 'houses',
                            name: 'Worker-Houses-Manage',
                            component: () => import('@/components/Worker/HousesView.vue')
                        },
                        {
                            path: 'add-house',
                            name: 'Add-Houses',
                            component: () => import('@/components/Worker/AddHousesView.vue')
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
                }
            ]
        },
        {
            path: '/admin',
            component: () => import('@/views/AdminView.vue'),
            meta: {requiresAuth: true, roles: ["admin"]},
            children: [
                {
                    path: ':username',
                    name: 'Admin-Main',
                    redirect: to => {
                        return {path: `/admin/${to.params.username}/accounts`};
                    },
                    children: [
                        {
                            path: 'accounts',
                            name: 'Account-Manage',
                            component: () => import('@/components/Admin/AccountsView.vue')
                        },
                        {
                            path: 'houses',
                            name: 'Admin-Houses-Manage',
                            component: () => import('@/components/Admin/HousesView.vue')
                        },
                        {
                            path: 'register-manage',
                            name: 'Register-Manage',
                            component: () => import('@/components/Admin/RegisterListView.vue')
                        }
                    ]
                }
            ]
        }
    ]
})

export default router

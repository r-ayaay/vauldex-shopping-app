// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import AppLayout from '../components/layouts/AppLayout.vue'

import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import HomeView from '../pages/Home.vue'
import Orders from '../pages/Orders.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: { requiresGuest: true },
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: { requiresGuest: true },
    },
    {
      path: '/',
      component: AppLayout,
      children: [
        { path: '', name: 'home', component: HomeView },
        { path: 'orders', name: 'orders', component: Orders },
      ],

      meta: { requiresAuth: true },
    },

    // default redirect
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to) => {
  const auth = useAuthStore()

  // If route requires auth and user is NOT logged in
  if (to.meta.requiresAuth && !auth.token) {
    return '/login'
  }

  // If the route is login/register but user is already logged in
  if (to.meta.requiresGuest && auth.token) {
    return '/dashboard'
  }
})

export default router

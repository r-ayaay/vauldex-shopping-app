<template>
  <!-- Sticky header -->
  <Header :username="username" @logout="logout" class="sticky top-0 z-50"></Header>

  <!-- Main content -->
  <main class="px-6 md:px-8 pt-6">
    <RouterView />
  </main>
</template>

<script setup lang="ts">
import Header from '@/components/Header.vue'

import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()

const username = computed(() => auth.user?.username || '')
function logout() {
  auth.logout()
  window.location.href = '/login'
}
</script>

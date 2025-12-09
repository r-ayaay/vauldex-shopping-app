<template>
  <!-- Sticky header -->
  <div class="sticky top-0 z-30">
    <Header :username="username" @logout="logout"></Header>
  </div>

  <!-- Main content -->
  <main class="px-6 md:px-8 py-6 bg-[#f5f4f7]">
    <RouterView />
  </main>
</template>

<script setup lang="ts">
import Header from '@/components/ui/organisms/Header.vue'

import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()

const username = computed(() => auth.user?.username || '')
function logout() {
  auth.logout()
  window.location.href = '/login'
}
</script>

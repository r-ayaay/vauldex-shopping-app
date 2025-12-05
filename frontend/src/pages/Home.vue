<template>
  <Header :username="username" @logout="logout"></Header>
  <div>hewo this is home :3BB</div>
</template>

<script setup lang="ts">
import Header from '../components/Header.vue'

import { computed } from 'vue'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()

const username = computed(() => auth.user?.username || '')
function logout() {
  auth.logout()
  window.location.href = '/login'
}
</script>

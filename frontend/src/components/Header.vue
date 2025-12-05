<template>
  <header class="flex bg-[#1f1f21] items-center justify-between h-16 px-6 md:px-8 shadow-sm">
    <router-link to="/">
      <h1 class="text-2xl font-semibold text-white pr-8 cursor-pointer">keyboredom</h1>
    </router-link>

    <!-- User dropdown -->
    <div class="relative" ref="userDropdownRef">
      <Button
        :label="username"
        @click="toggleUserDropdown"
        classNames="flex items-center gap-2 px-3 py-1.5 text-white rounded-md hover:bg-white hover:text-[#1C274C] focus:ring-2 focus:ring-[#1C274C]/40 transition-all font-medium"
      ></Button>

      <div
        v-if="userDropdownOpen"
        class="absolute right-0 mt-2 bg-white border border-gray-200 rounded-lg shadow-md z-50 min-w-[160px] overflow-hidden"
      >
        <Button
          label="Logout"
          @click="logoutHandler"
          classNames="flex w-full items-center gap-2 px-4 py-2 text-[#1C274C] hover:bg-[#f0f2f8] focus:bg-[#e3e7f3] transition-colors stroke-[#1C274C]"
        ></Button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import Button from './Button.vue'
import { useAuthStore } from '@/stores/auth'

interface User {
  id: number
  username: string
}

const auth = useAuthStore()

const router = useRouter()
const route = useRoute()
const { username, onLogout } = defineProps<{ username: string; onLogout: () => void }>()

const userDropdownOpen = ref(false)
const userDropdownRef = ref<HTMLElement | null>(null)

function toggleUserDropdown() {
  userDropdownOpen.value = !userDropdownOpen.value
}

function logoutHandler() {
  userDropdownOpen.value = false
  onLogout()
}

function handleClickOutside(e: MouseEvent) {
  if (userDropdownRef.value && !userDropdownRef.value.contains(e.target as Node))
    userDropdownOpen.value = false
}

// Lifecycle
onMounted(() => {
  document.addEventListener('mousedown', handleClickOutside)
})
onBeforeUnmount(() => document.removeEventListener('mousedown', handleClickOutside))
</script>

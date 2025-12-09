<template>
  <header class="flex items-center justify-between h-16 px-6 md:px-8 shadow-sm bg-white">
    <router-link to="/">
      <h1 class="text-2xl font-semibold text-[#1f1f21] pr-8 cursor-pointer">keyboredom</h1>
    </router-link>

    <div class="flex gap-4 items-center">
      <div class="relative inline-block" @click="openCartSidebar">
        <cartIcon class="w-6 h-6 text-[#1f1f21] cursor-pointer" />
        <span
          v-if="productCount > 0"
          class="absolute -top-3 -right-4 bg-red-500 text-white text-xs font-bold rounded-full p-1"
        >
          {{ productCount }}
        </span>
      </div>

      <!-- User dropdown -->
      <div class="relative" ref="userDropdownRef">
        <button
          @click="toggleUserDropdown"
          class="flex items-center gap-2 px-3 py-1.5 text-[#1f1f21] rounded-md hover:bg-[#1f1f21] hover:text-white focus:ring-2 focus:ring-[#1C274C]/40 transition-all font-medium cursor-pointer"
        >
          {{ username }}
        </button>

        <div
          v-if="userDropdownOpen"
          class="absolute right-0 mt-2 bg-white border border-gray-200 rounded-lg shadow-md z-50 min-w-[160px] overflow-hidden"
        >
          <router-link :to="{ name: 'orders' }">
            <button
              class="flex w-full items-center gap-2 px-4 py-2 text-[#1C274C] hover:bg-[#f0f2f8] cursor-pointer"
            >
              Orders
            </button>
          </router-link>
          <button
            @click="logoutHandler"
            class="flex w-full items-center gap-2 px-4 py-2 text-[#1C274C] hover:bg-[#f0f2f8] cursor-pointer"
          >
            Logout
          </button>
        </div>
      </div>
    </div>
  </header>

  <!-- Overlay -->
  <Transition name="fade">
    <div
      v-if="showCartSidebar"
      class="fixed inset-0 bg-black/40 z-40"
      @click="closeCartSidebar"
    ></div>
  </Transition>

  <!-- Sidebar -->
  <CartSidebar :open="showCartSidebar" @close="closeCartSidebar" />
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import api from '@/api/axios'
import cartIcon from './ui/icons/cart-icon.vue'
import CartSidebar from './ui/Cart-Sidebar.vue'
import { useWebSocketStore } from '@/stores/ws'

const { username, onLogout } = defineProps<{ username: string; onLogout: () => void }>()
const userDropdownOpen = ref(false)
const userDropdownRef = ref<HTMLElement | null>(null)
const productCount = ref<number>(0)
const showCartSidebar = ref(false)

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

async function fetchProductCount() {
  try {
    const res = await api.get<number>('/api/cart/count')
    productCount.value = res.data
  } catch (err) {
    console.error(err)
  }
}

// Sidebar open/close handlers with body scroll lock
function openCartSidebar() {
  showCartSidebar.value = true
  document.body.classList.add('overflow-hidden')
}

function closeCartSidebar() {
  showCartSidebar.value = false
  document.body.classList.remove('overflow-hidden')
}

onMounted(() => {
  fetchProductCount()
  document.addEventListener('mousedown', handleClickOutside)
})
onBeforeUnmount(() => document.removeEventListener('mousedown', handleClickOutside))

const wsStore = useWebSocketStore()
watch(
  () => wsStore.events,
  (events) => {
    const latest = events[events.length - 1]
    if (latest?.type === 'ITEM_ADDED' || latest?.type === 'CART_CHECKOUT') {
      fetchProductCount()
    }
  },
)
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.fade-enter-to,
.fade-leave-from {
  opacity: 1;
}
</style>

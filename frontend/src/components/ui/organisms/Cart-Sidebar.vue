<template>
  <div
    class="fixed top-0 right-0 h-screen w-96 max-w-full bg-white shadow-xl p-4 transform transition-transform duration-300 z-50 overflow-y-auto"
    :class="open ? 'translate-x-0' : 'translate-x-full'"
  >
    <button
      class="mb-4 text-gray-500 hover:text-black text-right cursor-pointer"
      @click="$emit('close')"
    >
      Close
    </button>

    <div class="flex justify-between">
      <h2 class="text-xl font-semibold mb-4">Your Cart</h2>
      <div class="font-semibold mb-4">Total: ₱{{ selectedTotal.toFixed(2) }}</div>
    </div>

    <div>
      <div
        v-for="item in cart?.items"
        :key="item.id"
        class="flex gap-4 mb-4 items-center bg-gray-50 p-2 rounded-lg"
      >
        <input type="checkbox" v-model="selectedIds" :value="item.id" class="h-5 w-5" />
        <img :src="item.productImageUrl" alt="Product Image" class="w-32 h-32 object-cover mb-2" />
        <div class="w-full">
          <h3 class="text-lg font-medium mb-2">{{ item.productName }}</h3>
          <div class="flex justify-between">
            <div>₱ {{ item.productPrice }}</div>
            <input
              type="number"
              class="w-1/2"
              v-model="item.quantity"
              @change="updateCartItemQuantity(item.id, item.quantity)"
            />
          </div>
        </div>
      </div>
    </div>

    <button
      class="mt-4 w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
      @click="checkoutSelected"
      :disabled="selectedIds.length === 0"
    >
      Checkout Selected
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import api from '@/api/axios'
import { useWebSocketStore } from '@/stores/ws'

defineProps<{ open: boolean }>()

interface Product {
  id: number
  productId: number
  productName: string
  productPrice: number
  quantity: number
  productImageUrl: string
}

interface Cart {
  id: number
  items: Product[]
  total: number
}

const cart = ref<Cart | null>(null)
const selectedIds = ref<number[]>([])

const selectedTotal = computed(() => {
  if (!cart.value) return 0
  return cart.value.items
    .filter((item) => selectedIds.value.includes(item.id))
    .reduce((sum, item) => sum + item.productPrice * item.quantity, 0)
})

async function fetchCartItems() {
  try {
    const res = await api.get<Cart>(`/api/cart`)
    cart.value = res.data
  } catch (err) {
    console.error(err)
  }
}

async function checkoutSelected() {
  if (selectedIds.value.length === 0) return
  try {
    await api.post('/api/cart/checkout', { cartItemIds: selectedIds.value })
    selectedIds.value = []
    fetchCartItems()
  } catch (err) {
    console.error(err)
  }
}

async function updateCartItemQuantity(cartItemId: number, quantity: number) {
  try {
    await api.put(`/api/cart/update/${cartItemId}`, null, { params: { quantity } })
  } catch (err) {
    console.error(err)
  }
}

onMounted(fetchCartItems)

const wsStore = useWebSocketStore()
watch(
  () => wsStore.events,
  (events) => {
    const latest = events[events.length - 1]
    if (
      latest?.type === 'ITEM_ADDED' ||
      latest?.type === 'CART_UPDATED' ||
      latest?.type === 'CART_CHECKOUT'
    ) {
      fetchCartItems()
    }
  },
)
</script>

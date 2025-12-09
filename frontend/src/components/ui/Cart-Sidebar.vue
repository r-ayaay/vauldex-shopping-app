<template>
  <!-- Sidebar -->
  <div
    class="absolute right-0 top-0 h-screen w-md bg-white shadow-xl p-4 transform transition-transform duration-300 z-50"
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

      <div class="font-semibold mb-4">Total: ₱{{ selectedTotal }}</div>
    </div>

    <div>
      <div
        v-for="item in cart?.items"
        :key="item.id"
        :name="item.productName"
        class="flex gap-4 mb-4 items-center bg-gray-50 p-2 rounded-lg"
      >
        <input type="checkbox" v-model="selectedIds" :value="item.id" class="h-5 w-5" />

        <img :src="item.productImageUrl" alt="Product Image" class="w-32 h-32 object-cover mb-2" />
        <div class="w-full">
          <h3 class="text-lg font-medium mb-2">
            {{ item.productName }}
          </h3>

          <div class="flex justify-between">
            <div>₱ {{ item.productPrice }}</div>
            <input
              class="w-1/2"
              type="number"
              v-model="item.quantity"
              @change="updateCartItemQuantity(item.id, item.quantity)"
            />
          </div>
        </div>
      </div>
    </div>

    <button
      class="mt-4 w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 cursor-pointer"
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

async function fetchCartItems() {
  try {
    const res = await api.get<Cart>(`/api/cart`)
    cart.value = res.data
  } catch (err) {
    console.error(err)
  }
}

const selectedTotal = computed(() => {
  if (!cart.value) return 0
  return cart.value.items
    .filter((item) => selectedIds.value.includes(item.id))
    .reduce((sum, item) => sum + item.productPrice * item.quantity, 0)
})

const selectedIds = ref<number[]>([])

async function checkoutSelected() {
  if (selectedIds.value.length === 0) return

  try {
    await api.post('/api/cart/checkout', { cartItemIds: selectedIds.value }).then(() => {
      console.log('Checkout successful for items:', selectedIds.value)
      selectedIds.value = [] // reset selection after checkout
    })
  } catch (err) {
    console.error('Error updating cart item:', err)
  }
}

async function updateCartItemQuantity(cartItemId: number, quantity: number) {
  try {
    await api.put(`/api/cart/update/${cartItemId}`, null, {
      params: { quantity }, // sends ?quantity=xx
    })
    console.log(`Updated cart item ${cartItemId} to quantity ${quantity}`)
  } catch (err) {
    console.error('Error updating cart item:', err)
  }
}

onMounted(() => {
  fetchCartItems()
})

import { useWebSocketStore } from '@/stores/ws'

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

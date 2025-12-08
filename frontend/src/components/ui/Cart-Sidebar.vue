<template>
  <!-- Sidebar -->
  <div
    class="absolute right-0 top-0 h-full w-md bg-white shadow-xl p-4 transform transition-transform duration-300 z-50"
    :class="open ? 'translate-x-0' : 'translate-x-full'"
  >
    <button class="mb-4 text-gray-500 hover:text-black text-right" @click="$emit('close')">
      Close
    </button>

    <h2 class="text-xl font-semibold mb-4">Your Cart</h2>

    <div>
      <div
        v-for="items in cart?.items"
        :key="items.id"
        :name="items.productName"
        class="flex gap-4 mb-4 items-center bg-gray-50 p-2 rounded-lg"
      >
        <img :src="items.productImageUrl" alt="Product Image" class="w-32 h-32 object-cover mb-2" />
        <div class="w-full">
          <h3 class="text-lg font-medium mb-2">
            {{ items.productName }}
          </h3>

          <div class="flex justify-between">
            <div>{{ items.productPrice }}</div>
            <div>{{ items.quantity }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
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

onMounted(() => {
  fetchCartItems()
})
</script>

<template>
  <div>
    <div>
      <h1 class="text-2xl font-bold mb-4">Order History</h1>
      <div v-if="orders.length > 0">
        <div v-for="order in orders" :key="order.id" class="border p-4 mb-4 rounded">
          <h2 class="text-xl font-semibold mb-2">Order #{{ order.id }}</h2>
          <p class="mb-2">Date: {{ new Date(order.createdAt).toLocaleDateString() }}</p>
          <p class="mb-2">Total Amount: ${{ order.totalAmount.toFixed(2) }}</p>
          <h3 class="font-semibold mb-2">Items:</h3>
          <ul class="list-disc list-inside">
            <li v-for="item in order.items" :key="item.id">
              {{ item.productName }} - ${{ item.productPrice }} x {{ item.quantity }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/api/axios'

interface orderItem {
  id: number
  productId: number
  productName: string
  productPrice: number
  quantity: number
  productImageUrl: string
}

interface Order {
  id: number
  createdAt: string
  totalAmount: number
  items: orderItem[]
}

const orders = ref<Order[]>([])

const fetchOrders = async () => {
  try {
    const res = await api.get<Order[]>('/api/orders')
    orders.value = res.data
    console.log(orders.value)
  } catch (err) {
    console.error(err)
  }
}

onMounted(() => {
  fetchOrders()
})
</script>

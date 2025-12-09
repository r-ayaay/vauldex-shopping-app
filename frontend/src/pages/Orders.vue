<template>
  <div class="flex gap-4" style="height: calc(100vh - 112px)">
    <!-- Left side: Table of contents -->
    <div class="w-1/4 p-4 rounded-md bg-white">
      <h1 class="text-2xl font-bold mb-4">Order History</h1>
      <ul class="space-y-2 overflow-y-auto">
        <li
          v-for="order in orders"
          :key="order.id"
          @click="scrollToOrder(order.id)"
          :class="[
            'cursor-pointer p-2 rounded flex justify-between',
            activeOrderId === order.id ? 'bg-blue-100 font-semibold' : 'hover:bg-gray-100',
          ]"
        >
          <div>Order #{{ order.id }}</div>
          <div>{{ new Date(order.createdAt).toLocaleDateString() }}</div>
        </li>
      </ul>
    </div>

    <!-- Right side: Scrollable orders -->
    <div id="orders-panel" class="w-3/4 overflow-y-auto relative" v-if="orders.length > 0">
      <div
        v-for="order in orders"
        :key="order.id"
        :id="'order-' + order.id"
        class="border-gray-100 p-4 mb-4 rounded-md bg-white"
      >
        <div class="flex justify-between border-b border-gray-300 pb-2">
          <h2 class="text-xl font-semibold mb-2">Order #{{ order.id }}</h2>
          <h3 class="mb-2">Date: {{ new Date(order.createdAt).toLocaleDateString() }}</h3>
        </div>
        <div class="border-b border-gray-300 p-8">
          <div v-for="item in order.items" :key="item.id" class="flex">
            <img
              :src="item.productImageUrl"
              alt="Product Image"
              class="w-16 h-16 object-scale-down mr-4"
            />
            <div class="w-full">
              <div>{{ item.productName }}</div>
              <div class="flex justify-between">
                <div>x{{ item.quantity }}</div>
                <div>₱{{ item.productPrice }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="flex justify-between px-8 pt-4">
          <button
            class="bg-gray-100 p-2 rounded-md cursor-pointer hover:bg-gray-300"
            @click="deleteOrder(order.id)"
          >
            Cancel Order
          </button>
          <p class="mb-2">Total Amount: ₱{{ order.totalAmount.toFixed(2) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import api from '@/api/axios'
import { useWebSocketStore } from '@/stores/ws'

interface OrderItem {
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
  items: OrderItem[]
}

const orders = ref<Order[]>([])
const activeOrderId = ref<number | null>(null)
const wsStore = useWebSocketStore()

const fetchOrders = async () => {
  try {
    const res = await api.get<Order[]>('/api/orders')
    orders.value = res.data
  } catch (err) {
    console.error(err)
  }
}

async function deleteOrder(orderId: number) {
  try {
    await api.delete(`/api/orders/cancel/${orderId}`)
    console.log(`Deleted order ${orderId}`)
  } catch (err) {
    console.error('Error deleting order', err)
  }
}

function scrollToOrder(orderId: number) {
  const el = document.getElementById('order-' + orderId)
  if (el) el.scrollIntoView({ behavior: 'smooth', block: 'start' })
}

function handleScroll() {
  const rightPanel = document.getElementById('orders-panel')
  if (!rightPanel) return

  const containerTop = rightPanel.getBoundingClientRect().top
  let closestOrderId: number | null = null
  let closestDistance = Infinity

  orders.value.forEach((order) => {
    const el = document.getElementById('order-' + order.id)
    if (el) {
      const distance = Math.abs(el.getBoundingClientRect().top - containerTop)
      if (distance < closestDistance) {
        closestDistance = distance
        closestOrderId = order.id
      }
    }
  })

  activeOrderId.value = closestOrderId
}

// Fetch orders on mount
onMounted(() => {
  fetchOrders()
  const rightPanel = document.getElementById('orders-panel')
  rightPanel?.addEventListener('scroll', handleScroll)
})

// Remove scroll listener on unmount
onBeforeUnmount(() => {
  const rightPanel = document.getElementById('orders-panel')
  rightPanel?.removeEventListener('scroll', handleScroll)
})

// Listen to WebSocket events
watch(
  () => wsStore.events,
  (events) => {
    const latest = events[events.length - 1]
    if (latest?.type === 'CART_CHECKOUT' || latest?.type === 'ORDER_CANCELLED') {
      fetchOrders()
    }
  },
)
</script>

<template>
  <div class="bg-gray-100 border rounded-2xl p-4">
    <h2 class="text-lg font-semibold">{{ name }}</h2>
    <img :src="images[0]" alt="product image" class="w-full h-60 object-contain mb-2" />
    <p class="text-gray-600">{{ description }}</p>
    <p class="font-bold mt-1">₱{{ price.toFixed(2) }}</p>
    <div class="mt-2 flex items-center gap-2">
      <input
        type="number"
        min="1"
        v-model.number="quantity"
        class="w-16 border rounded px-2 py-1"
      />
      <button
        @click="addToCart"
        class="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700 cursor-pointer"
      >
        Add to Cart
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  id: number
  name: string
  price: number
  description: string
  images: string[]
}

const props = defineProps<Props>()
const quantity = ref(1)

const addToCart = async () => {
  try {
    const res = await fetch(
      `http://localhost:8080/api/cart/add?productId=${props.id}&quantity=${quantity.value}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`, // adjust based on where you store the JWT
        },
      },
    )
    if (!res.ok) throw new Error('Failed to add to cart')
    alert('Added to cart!')
  } catch (err) {
    console.error(err)
    alert('Could not add product to cart.')
  }
}
</script>

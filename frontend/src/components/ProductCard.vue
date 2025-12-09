<template>
  <div class="bg-white rounded-2xl p-4 flex flex-col">
    <!-- Product Name -->
    <h2 class="text-lg font-semibold line-clamp-2 leading-tight h-[3rem] mb-2">
      {{ name }}
    </h2>

    <!-- Product Image -->
    <img :src="images[0]" alt="product image" class="w-full h-60 object-contain mb-2" />

    <!-- Description -->
    <p class="text-gray-600 leading-tight line-clamp-2 h-[3rem] mb-2">
      {{ description }}
    </p>

    <!-- Quantity + Button -->
    <div class="flex items-center gap-2 justify-between mt-auto">
      <!-- Price -->
      <p class="font-bold">₱{{ price.toFixed(2) }}</p>
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

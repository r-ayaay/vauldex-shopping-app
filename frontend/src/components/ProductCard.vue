<template>
  <div class="bg-white rounded-2xl p-4 flex flex-col">
    <!-- Product Name -->
    <h2 class="text-lg font-semibold line-clamp-2 leading-tight h-[3rem] mb-2">
      {{ name }}
    </h2>

    <!-- Product Image -->
    <div class="relative mb-2">
      <img
        :src="images[0]"
        alt="product image"
        class="w-full h-60 object-contain transition-transform duration-200 cursor-pointer hover:scale-110"
        @click="openModal"
      />
    </div>

    <!-- Description -->
    <p class="text-gray-600 leading-tight line-clamp-2 h-[3rem] mb-2">
      {{ description }}
    </p>

    <!-- Price + Button -->
    <div class="flex items-center gap-2 justify-between mt-auto">
      <p class="font-bold">₱{{ price.toFixed(2) }}</p>
      <button
        @click="addToCart"
        class="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700 cursor-pointer"
      >
        Add to Cart
      </button>
    </div>

    <!-- FULLSCREEN LIGHTBOX -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-90 flex flex-col items-center justify-center z-50"
      @click.self="closeModal"
    >
      <!-- Close Button -->
      <button @click="closeModal" class="absolute top-6 right-6 text-white text-3xl font-bold">
        ✕
      </button>

      <!-- Main Image -->

      <img
        :key="images[currentIndex]"
        :src="images[currentIndex]"
        class="max-h-[80vh] max-w-[90vw] object-contain mb-6"
      />

      <!-- Navigation Arrows -->
      <button
        v-if="images.length > 1"
        @click.stop="prevImage"
        class="absolute left-6 text-white text-4xl font-bold select-none"
      >
        ‹
      </button>

      <button
        v-if="images.length > 1"
        @click.stop="nextImage"
        class="absolute right-6 text-white text-4xl font-bold select-none"
      >
        ›
      </button>

      <!-- Thumbnails Row -->
      <div
        v-if="images.length > 1"
        class="flex gap-3 overflow-x-auto px-4 py-2 bg-black bg-opacity-40 rounded-xl"
      >
        <img
          v-for="(img, i) in images"
          :key="i"
          :src="img"
          @click.stop="currentIndex = i"
          class="h-20 w-20 object-cover rounded cursor-pointer transition-all border-2"
          :class="
            i === currentIndex
              ? 'border-white scale-110'
              : 'border-transparent opacity-70 hover:opacity-100'
          "
        />
      </div>
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

const emit = defineEmits(['add-to-cart'])

const showModal = ref(false)
const currentIndex = ref(0)

function openModal() {
  currentIndex.value = 0
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

function nextImage() {
  currentIndex.value = (currentIndex.value + 1) % props.images.length
}

function prevImage() {
  currentIndex.value = (currentIndex.value - 1 + props.images.length) % props.images.length
}
</script>

<style></style>

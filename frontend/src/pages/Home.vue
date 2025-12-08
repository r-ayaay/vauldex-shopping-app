<template>
  <h1 class="text-2xl font-bold mb-4">Products</h1>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
    <ProductCard v-for="product in products" :key="product.id" v-bind="product" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import api from '@/api/axios'

interface Product {
  id: number
  name: string
  price: number
  description: string
  images: string[]
}

const products = ref<Product[]>([])

const fetchProducts = async () => {
  try {
    const res = await api.get<Product[]>('/api/products')
    products.value = res.data
    console.log('Fetched products:', products.value)
  } catch (err) {
    console.error(err)
  }
}

onMounted(fetchProducts)
</script>

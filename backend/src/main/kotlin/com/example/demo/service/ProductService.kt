package com.example.demo.service

import com.example.demo.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product =
        productRepository.findById(id).orElseThrow { RuntimeException("Product not found") }

    fun createProduct(product: Product): Product = productRepository.save(product)
}

package com.example.demo.service

import com.example.demo.dto.ProductDTO
import com.example.demo.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<ProductDTO> =
        productRepository.findAll().map { it.toDTO() }

    fun getProductById(id: Long): ProductDTO =
        productRepository.findById(id).orElseThrow { RuntimeException("Product not found") }.toDTO()

    fun createProduct(product: Product): Product = productRepository.save(product)

    private fun Product.toDTO(): ProductDTO =
        ProductDTO(
            id = this.id,
            name = this.name,
            price = this.price,
            description = this.description,
            images = this.images.map { it.url } // all image URLs
        )
}


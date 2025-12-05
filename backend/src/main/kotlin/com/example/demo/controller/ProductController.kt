package com.example.demo.controller

import com.example.demo.entity.Product
import com.example.demo.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts() = productService.getAllProducts()

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long) = productService.getProductById(id)

    @PostMapping
    fun createProduct(@RequestBody product: Product) = productService.createProduct(product)
}

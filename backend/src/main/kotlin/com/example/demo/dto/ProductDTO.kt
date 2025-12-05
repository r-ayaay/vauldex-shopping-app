package com.example.demo.dto

data class ProductDTO(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val images: List<String> // all image URLs
)

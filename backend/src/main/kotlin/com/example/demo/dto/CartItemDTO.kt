package com.example.demo.dto

data class CartItemDTO(
    val id: Long,
    val productId: Long,
    val productName: String,
    val productPrice: Double,
    val quantity: Int
)

package com.example.demo.dto

data class CartResponseDTO(
    val id: Long,
    val items: List<CartItemDTO>,
    val total: Double
)

package com.example.demo.dto

import java.time.LocalDateTime

data class OrderResponseDTO(
    val id: Long,
    val createdAt: LocalDateTime,
    val totalAmount: Double,
    val items: List<OrderItemDTO>,
    val status: String
)

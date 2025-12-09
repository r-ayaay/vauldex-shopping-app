package com.example.demo.controller

import com.example.demo.dto.CheckoutRequest
import com.example.demo.dto.OrderResponseDTO
import com.example.demo.service.OrderService
import com.example.demo.util.AuthenticatedUser
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService, private val authenticatedUser: AuthenticatedUser) {

    @GetMapping
    fun getUserOrders(): List<OrderResponseDTO> {
        val userId = authenticatedUser.getUserId()
        return orderService.getOrdersDTOForUser(userId)
    }

    @GetMapping("/{userId}")
    fun getUserOrders(@PathVariable userId: Long): List<OrderResponseDTO> {
        return orderService.getOrdersDTOForUser(userId)
    }


    @PutMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable orderId: Long) {
        orderService.cancelOrder(orderId)
    }

    @PutMapping("/complete/{orderId}")
    fun completeOrder(@PathVariable orderId: Long) {
        orderService.completeOrder(orderId)
    }
}

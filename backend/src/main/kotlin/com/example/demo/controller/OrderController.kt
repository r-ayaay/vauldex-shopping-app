package com.example.demo.controller

import com.example.demo.dto.CheckoutRequest
import com.example.demo.dto.OrderResponseDTO
import com.example.demo.service.OrderService
import com.example.demo.util.AuthenticatedUser
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService, private val authenticatedUser: AuthenticatedUser) {


    @PostMapping("/checkout")
    fun checkout(@RequestBody request: CheckoutRequest): OrderResponseDTO {
        val userId = authenticatedUser.getUserId()
        return orderService.checkoutDTO(userId, request.cartItemIds)
    }

    @GetMapping
    fun getUserOrders(): List<OrderResponseDTO> {
        val userId = authenticatedUser.getUserId()
        return orderService.getOrdersDTOForUser(userId)
    }


    @PostMapping("/checkout/{userId}")
    fun checkout(
        @PathVariable userId: Long,
        @RequestBody request: CheckoutRequest
    ): OrderResponseDTO {

        return orderService.checkoutDTO(userId, request.cartItemIds)
    }


    @GetMapping("/{userId}")
    fun getUserOrders(@PathVariable userId: Long): List<OrderResponseDTO> {
        return orderService.getOrdersDTOForUser(userId)
    }


    @DeleteMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable orderId: Long) {
        orderService.cancelOrder(orderId)
    }
}

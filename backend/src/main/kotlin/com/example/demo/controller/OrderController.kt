package com.example.demo.controller

import com.example.demo.dto.CheckoutRequest
import com.example.demo.dto.OrderResponseDTO
import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

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

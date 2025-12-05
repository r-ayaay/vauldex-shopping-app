package com.example.demo.controller

import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping("/checkout/{userId}")
    fun checkout(@PathVariable userId: Long, @RequestBody cartItemIds: List<Long>) =
        orderService.checkout(userId, cartItemIds)

    @GetMapping("/{userId}")
    fun getUserOrders(@PathVariable userId: Long) = orderService.getOrdersForUser(userId)

    @DeleteMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable orderId: Long) {
        orderService.cancelOrder(orderId)
    }
}

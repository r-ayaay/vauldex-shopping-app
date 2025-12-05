package com.example.demo.service

import com.example.demo.entity.Order
import com.example.demo.entity.OrderItem
import com.example.demo.entity.User

import com.example.demo.repository.CartItemRepository
import com.example.demo.repository.CartRepository
import com.example.demo.repository.OrderItemRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val cartItemRepository: CartItemRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun checkout(userId: Long, cartItemIds: List<Long>): Order {
        val cart = cartRepository.findByUserId(userId) ?: throw RuntimeException("Cart not found")
        val itemsToCheckout = cartItemRepository.findAllById(cartItemIds)

        val totalAmount = itemsToCheckout.sumOf { it.product.price * it.quantity }
        val order = orderRepository.save(Order(user = User(id = userId, username = "", password = ""), totalAmount = totalAmount))

        itemsToCheckout.forEach { item ->
            val orderItem = OrderItem(order = order, product = item.product, quantity = item.quantity)
            orderItemRepository.save(orderItem)
            cartItemRepository.delete(item)
        }

        return order
    }

    fun getOrdersForUser(userId: Long): List<Order> =
        orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId)

    @Transactional
    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow { RuntimeException("Order not found") }

        if (order.createdAt.isAfter(LocalDateTime.now().minusDays(7))) {
            throw RuntimeException("Orders can only be cancelled if created at least 7 days ago")
        }

        orderRepository.delete(order)
    }
}

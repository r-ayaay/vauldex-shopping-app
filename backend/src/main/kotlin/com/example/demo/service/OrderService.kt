package com.example.demo.service

import com.example.demo.dto.OrderItemDTO
import com.example.demo.dto.OrderResponseDTO
import com.example.demo.entity.Order
import com.example.demo.entity.OrderItem
import com.example.demo.entity.User

import com.example.demo.repository.CartItemRepository
import com.example.demo.repository.CartRepository
import com.example.demo.repository.OrderItemRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.ws.SocketHandler
import com.example.demo.ws.WebSocketEvent

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val cartItemRepository: CartItemRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val socketHandler: SocketHandler
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

        if (order.createdAt.isBefore(LocalDateTime.now().minusDays(7))) {
            throw RuntimeException("Orders can only be cancelled if created less than 7 days ago")
        }

        val event = WebSocketEvent(
            type = "ORDER_CANCELLED",
            payload = mapOf(
                "orderId" to order.id,
            )
        )

        socketHandler.broadcast(event)

        order.status = "CANCELLED"

        orderRepository.save(order)
    }

    @Transactional
    fun completeOrder(orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow { RuntimeException("Order not found") }

        if (order.createdAt.isBefore(LocalDateTime.now().minusDays(7))) {
            throw RuntimeException("Orders can only be completed if created at least 7 days ago")
        }

        val event = WebSocketEvent(
            type = "ORDER_COMPLETED",
            payload = mapOf(
                "orderId" to order.id,
            )
        )

        socketHandler.broadcast(event)

        order.status = "COMPLETED"

        orderRepository.save(order)
    }

    fun getOrdersDTOForUser(userId: Long): List<OrderResponseDTO> {
        val orders = getOrdersForUser(userId)
        return orders.map { order ->
            val itemsDTO = order.items.map { item ->
                OrderItemDTO(
                    id = item.id,
                    productId = item.product.id,
                    productName = item.product.name,
                    productPrice = item.product.price,
                    productImageUrl = item.product.images.firstOrNull()?.url,
                    quantity = item.quantity,
                )
            }
            OrderResponseDTO(
                id = order.id,
                createdAt = order.createdAt,
                totalAmount = order.totalAmount,
                items = itemsDTO,
                status = order.status
            )
        }
    }

    @Transactional
    fun checkoutDTO(userId: Long, cartItemIds: List<Long>): OrderResponseDTO {
        val cart = cartRepository.findByUserId(userId) ?: throw RuntimeException("Cart not found")
        val itemsToCheckout = cartItemRepository.findAllById(cartItemIds)

        val totalAmount = itemsToCheckout.sumOf { it.product.price * it.quantity }
        val order = orderRepository.save(Order(user = User(id = userId, username = "", password = ""), totalAmount = totalAmount))

        itemsToCheckout.forEach { item ->
            val orderItem = OrderItem(order = order, product = item.product, quantity = item.quantity)
            orderItemRepository.save(orderItem)
            cartItemRepository.delete(item)
        }

        // Map to DTO
        val itemsDTO = order.items.map { item ->
            OrderItemDTO(
                id = item.id,
                productId = item.product.id,
                productName = item.product.name,
                productPrice = item.product.price,
                productImageUrl = item.product.images.firstOrNull()?.url,
                quantity = item.quantity
            )
        }

        val event = WebSocketEvent(
            type = "CART_CHECKOUT",
            payload = mapOf(
                "cartId" to cart.id,
                "checkedOutItemIds" to cartItemIds
            )
        )


        socketHandler.broadcast(event)

        return OrderResponseDTO(
            id = order.id,
            createdAt = order.createdAt,
            totalAmount = order.totalAmount,
            items = itemsDTO,
            status = "PENDING"
        )
    }

}

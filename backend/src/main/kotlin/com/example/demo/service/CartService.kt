package com.example.demo.service

import com.example.demo.dto.CartItemDTO
import com.example.demo.dto.CartResponseDTO
import com.example.demo.entity.Cart
import com.example.demo.entity.CartItem
import com.example.demo.entity.User
import com.example.demo.repository.CartItemRepository
import com.example.demo.repository.CartRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.ws.SocketHandler
import com.example.demo.ws.WebSocketEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository,
    private val socketHandler: SocketHandler
) {

    fun getCartForUser(userId: Long): Cart {
        return cartRepository.findByUserId(userId) ?: Cart(user = User(id = userId, username = "", password = ""))
    }

    @Transactional
    fun addProductToCart(userId: Long, productId: Long, quantity: Int) {
        val cart = cartRepository.findByUserId(userId) ?: cartRepository.save(Cart(user = User(id = userId, username = "", password = "")))
        val product = productRepository.findById(productId).orElseThrow { RuntimeException("Product not found") }

        val existingItem = cart.items.find { it.product.id == productId }
        if (existingItem != null) {
            existingItem.quantity += quantity
            cartItemRepository.save(existingItem)
        } else {
            val newItem = CartItem(cart = cart, product = product, quantity = quantity)
            cartItemRepository.save(newItem)
        }

        val event = WebSocketEvent(
            type = "ITEM_ADDED",
            payload = mapOf(
                "product" to product.name,
                "quantity" to quantity
            )
        )

        socketHandler.broadcast(event)
    }

    @Transactional
    fun updateCartItemQuantity(cartItemId: Long, quantity: Int) {
        val item = cartItemRepository.findById(cartItemId).orElseThrow { RuntimeException("Cart item not found") }
        item.quantity = quantity
        cartItemRepository.save(item)
    }

    @Transactional
    fun removeCartItem(cartItemId: Long) {
        cartItemRepository.deleteById(cartItemId)
    }

    fun calculateTotal(cart: Cart): Double {
        return cart.items.sumOf { it.product.price * it.quantity }
    }

    fun getCartDTOForUser(userId: Long): CartResponseDTO {
        val cart = getCartForUser(userId)
        val itemsDTO = cart.items.map { item ->
            CartItemDTO(
                id = item.id,
                productId = item.product.id,
                productName = item.product.name,
                productPrice = item.product.price,
                productImageUrl = item.product.images.firstOrNull()?.url,
                quantity = item.quantity
            )
        }
        val total = calculateTotal(cart)
        return CartResponseDTO(
            id = cart.id,
            items = itemsDTO,
            total = total
        )
    }

}

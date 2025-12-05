package com.example.demo.controller

import com.example.demo.dto.CartResponseDTO
import com.example.demo.service.CartService
import com.example.demo.util.AuthenticatedUser
import org.springframework.web.bind.annotation.*


//Handles fetching cart, adding, updating, removing items, and getting total.
@RestController
@RequestMapping("/api/cart")
class CartController(private val cartService: CartService, private val authenticatedUser: AuthenticatedUser) {

    @GetMapping
    fun getCart(): CartResponseDTO {
        val userId = authenticatedUser.getUserId()
        return cartService.getCartDTOForUser(userId)
    }

    @PostMapping("/add")
    fun addToCart(
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ) {
        val userId = authenticatedUser.getUserId()
        cartService.addProductToCart(userId, productId, quantity)
    }

    @GetMapping("/total")
    fun getTotal(): Double {
        val userId = authenticatedUser.getUserId()
        val cart = cartService.getCartForUser(userId)
        return cartService.calculateTotal(cart)
    }

    @GetMapping("/{userId}")
    fun getCart(@PathVariable userId: Long): CartResponseDTO {
        return cartService.getCartDTOForUser(userId)
    }


    @PostMapping("/{userId}/add")
    fun addToCart(
        @PathVariable userId: Long,
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ) {
        cartService.addProductToCart(userId, productId, quantity)
    }

    @PutMapping("/update/{cartItemId}")
    fun updateQuantity(@PathVariable cartItemId: Long, @RequestParam quantity: Int) {
        cartService.updateCartItemQuantity(cartItemId, quantity)
    }

    @DeleteMapping("/remove/{cartItemId}")
    fun removeItem(@PathVariable cartItemId: Long) {
        cartService.removeCartItem(cartItemId)
    }

    @GetMapping("/{userId}/total")
    fun getTotal(@PathVariable userId: Long): Double {
        val cart = cartService.getCartForUser(userId)
        return cartService.calculateTotal(cart)
    }
}

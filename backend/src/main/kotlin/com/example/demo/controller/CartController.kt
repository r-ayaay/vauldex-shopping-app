package com.example.demo.controller

import com.example.demo.service.CartService
import org.springframework.web.bind.annotation.*


//Handles fetching cart, adding, updating, removing items, and getting total.
@RestController
@RequestMapping("/api/cart")
class CartController(private val cartService: CartService) {

    @GetMapping("/{userId}")
    fun getCart(@PathVariable userId: Long) = cartService.getCartForUser(userId)

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

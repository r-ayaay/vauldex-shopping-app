package com.example.demo.repository

import com.example.demo.entity.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findAllByCartId(cartId: Long): List<CartItem>
}

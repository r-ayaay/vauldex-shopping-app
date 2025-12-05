package com.example.demo.entity

import jakarta.persistence.*

@Entity
data class CartItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "cart_id")
    val cart: Cart,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    var quantity: Int
)

package com.example.demo.repository

import com.example.demo.entity.ProductImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepository : JpaRepository<ProductImage, Long>

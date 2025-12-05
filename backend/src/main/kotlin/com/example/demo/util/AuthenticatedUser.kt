package com.example.demo.util

import com.example.demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticatedUser(private val userRepository: UserRepository) {

    fun getUserId(): Long {
        val userIdString = SecurityContextHolder.getContext().authentication.name
        return userIdString.toLong()
    }
}

package com.example.demo.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.text.toByteArray
import kotlin.text.toLong
import kotlin.toString

@Suppress("DEPRECATION")
@Component
class JwtUtil(
    @param:Value("\${jwt.secret}") private val secret: String
) {

    private val expirationMs = 1000 * 60 * 60 * 24L // 24 hours

    fun generateToken(userId: Long): String {
        val now = Date()
        val expiry = Date(now.time + expirationMs)
        return Jwts.builder()
            .setSubject(userId.toString()) // store the ID in `sub` claim
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
            .compact()
    }


    fun extractUsername(token: String): String {
        return getClaims(token).subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret.toByteArray())
            .parseClaimsJws(token)
            .body
    }
}

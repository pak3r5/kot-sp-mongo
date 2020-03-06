package com.gitcs.kotlin.security.jwt

import com.gitcs.kotlin.security.service.CustomUserDetails
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    @Value("\${gtics.app.jwtSecret}")
    val jwtSecret: String? = null

    @Value("\${gtics.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal: CustomUserDetails = authentication.getPrincipal() as CustomUserDetails
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date())
                .setExpiration(Date(Date().getTime() + jwtExpirationMs))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun getUserNameFromJwtToken(token: String?): String? {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject()
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        }catch (e:SignatureException) {
            return false
        } catch (e:MalformedJwtException) {
            return false
        } catch (e:UnsupportedJwtException) {
            return false
        } catch (e:IllegalArgumentException) {
            return false
        }
        return false
    }
}
package com.gitcs.kotlin.rest

import com.gitcs.kotlin.payload.request.LoginRequest
import com.gitcs.kotlin.payload.response.JwtResponse
import com.gitcs.kotlin.security.jwt.JwtUtils
import com.gitcs.kotlin.security.service.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest?.username, loginRequest?.password))
        SecurityContextHolder .getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(SecurityContextHolder.getContext().authentication)
        val customUserDetails: CustomUserDetails = authentication.getPrincipal() as CustomUserDetails
        val roles: List<String> = customUserDetails.getAuthorities().stream()
                .map({ item -> item.getAuthority() })
                .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(JwtResponse(jwt,
                customUserDetails.id,
                customUserDetails.username,
                roles))
    }

}
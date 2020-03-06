package com.gitcs.kotlin.rest

import com.gitcs.kotlin.model.Role
import com.gitcs.kotlin.model.User
import com.gitcs.kotlin.model.enum.EnumRole
import com.gitcs.kotlin.payload.request.LoginRequest
import com.gitcs.kotlin.payload.request.SignupRequest
import com.gitcs.kotlin.payload.response.JwtResponse
import com.gitcs.kotlin.payload.response.MessageResponse
import com.gitcs.kotlin.repository.RoleRepository
import com.gitcs.kotlin.repository.UserRepository
import com.gitcs.kotlin.security.jwt.JwtUtils
import com.gitcs.kotlin.security.service.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest?.username, loginRequest?.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails: CustomUserDetails = authentication.getPrincipal() as CustomUserDetails
        val roles: List<String> = userDetails.getAuthorities().stream()
                .map({ item -> item.getAuthority() })
                .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(JwtResponse(jwt,
                userDetails.getId(),
                userDetails.username,
                roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*> {
        var existUser = signUpRequest?.username?.let { userRepository?.existsByUsername(it) }
        if (existUser!!) {
            return ResponseEntity
                    .badRequest()
                    .body<Any>(MessageResponse("Error: Username is already taken!"))
        }

        // Create new user's account
        val user = User("",signUpRequest?.username,
                encoder!!.encode(signUpRequest?.password),signUpRequest?.name,signUpRequest?.lastName)
        val strRoles: Set<String>? = signUpRequest?.roles
        val roles: MutableSet<Role> = HashSet<Role>()
        if (strRoles == null) {
            val userRole: Role = roleRepository!!.findByName(EnumRole.ROLE_USER)
                    //.orElseThrow { RuntimeException("Error: Role is not found.") }
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "root" -> {
                        val adminRole: Role = roleRepository!!.findByName(EnumRole.ROLE_ADMIN)
                                //.orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(adminRole)
                    }
                    "admin" -> {
                        val modRole: Role = roleRepository!!.findByName(EnumRole.ROLE_ADMIN)
                                //.orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(modRole)
                    }
                    else -> {
                        val userRole: Role = roleRepository!!.findByName(EnumRole.ROLE_USER)
                                //.orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(userRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository!!.save(user)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}
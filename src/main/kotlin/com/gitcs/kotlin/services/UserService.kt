package com.gitcs.kotlin.services

import com.gitcs.kotlin.model.Role
import com.gitcs.kotlin.model.User
import com.gitcs.kotlin.model.enum.EnumRole
import com.gitcs.kotlin.payload.request.SignupRequest
import com.gitcs.kotlin.payload.response.MessageResponse
import com.gitcs.kotlin.repository.RoleRepository
import com.gitcs.kotlin.repository.UserRepository
import com.gitcs.kotlin.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.HashSet
import java.util.function.Consumer

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    fun getUsers():List<User> = userRepository.findAll()

    fun createUser(signUpRequest: SignupRequest?): ResponseEntity<*> {
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
                    "ROLE_ROOT" -> {
                        val adminRole: Role = roleRepository!!.findByName(EnumRole.ROLE_ROOT)
                        //.orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(adminRole)
                    }
                    "ROLE_ADMIN" -> {
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
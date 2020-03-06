package com.gitcs.kotlin.security.service

import com.gitcs.kotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
open class CustomUserDetailsService () : UserDetailsService {

    @Autowired
    lateinit var userRepository:UserRepository

    override fun loadUserByUsername(username: String): UserDetails? {
        var user: com.gitcs.kotlin.model.User? = userRepository.findByUsername(username)
        var authorities:List<SimpleGrantedAuthority>  = Arrays.asList(SimpleGrantedAuthority("user"));
        if (user != null) {
            return User(user.username,user.password,authorities)
        }
        return null
    }
}
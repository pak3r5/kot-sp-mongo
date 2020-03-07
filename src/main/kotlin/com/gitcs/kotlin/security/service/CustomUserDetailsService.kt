package com.gitcs.kotlin.security.service

import com.gitcs.kotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
open class CustomUserDetailsService () : UserDetailsService {

    @Autowired
    lateinit var userRepository:UserRepository

    @Override
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails? {
        val user: com.gitcs.kotlin.model.User? = userRepository.findByUsername(username)
                //.orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }
        return CustomUserDetails.build(user!!)
    }
}
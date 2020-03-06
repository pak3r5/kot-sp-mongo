package com.gitcs.kotlin.services

import com.gitcs.kotlin.model.User
import com.gitcs.kotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun getUsers():List<User> = userRepository.findAll()
}
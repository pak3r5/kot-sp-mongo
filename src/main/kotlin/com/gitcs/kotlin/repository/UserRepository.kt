package com.gitcs.kotlin.repository

import com.gitcs.kotlin.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository:MongoRepository<User,String> {
    fun findByUsername(username:String):User?
    fun existsByUsername(username: String):Boolean
}
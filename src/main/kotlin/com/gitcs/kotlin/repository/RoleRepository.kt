package com.gitcs.kotlin.repository

import com.gitcs.kotlin.model.enum.EnumRole
import com.gitcs.kotlin.model.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository:MongoRepository<Role,String> {
    fun findByName(name:EnumRole):Role
}
package com.gitcs.kotlin.services

import com.gitcs.kotlin.model.Role
import com.gitcs.kotlin.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService {
    @Autowired
    lateinit var roleRepository: RoleRepository

    fun getRole():List<Role> = roleRepository.findAll()
}
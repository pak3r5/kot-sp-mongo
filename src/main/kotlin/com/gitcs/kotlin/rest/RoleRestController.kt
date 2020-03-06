package com.gitcs.kotlin.rest

import com.gitcs.kotlin.model.Role
import com.gitcs.kotlin.services.RoleService
import com.gitcs.kotlin.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstants.URL_API_ROLE)
class RoleRestController {
    @Autowired
    lateinit var roleService: RoleService

    @GetMapping
    fun getAll():List<Role> = roleService.getRole()
}
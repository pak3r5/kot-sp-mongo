package com.gitcs.kotlin.rest

import com.gitcs.kotlin.model.User
import com.gitcs.kotlin.services.UserService
import com.gitcs.kotlin.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstants.URL_API_USER)
class UserRestController {
    @Autowired
    lateinit var userService: UserService
    @GetMapping
    fun getAll():List<User> = userService.getUsers()
}
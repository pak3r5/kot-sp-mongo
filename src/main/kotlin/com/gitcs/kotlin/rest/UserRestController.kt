package com.gitcs.kotlin.rest

import com.gitcs.kotlin.model.User
import com.gitcs.kotlin.payload.request.SignupRequest
import com.gitcs.kotlin.services.UserService
import com.gitcs.kotlin.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(AppConstants.URL_API_USER)
class UserRestController {
    @Autowired
    lateinit var userService: UserService
    @GetMapping
    fun getAll():List<User> = userService.getUsers()
    @PostMapping
    fun createUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*> = userService.createUser(signUpRequest)
}
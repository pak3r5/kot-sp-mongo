package com.gitcs.kotlin.payload.response

class JwtResponse(var accessToken: String?, var id: String?, var username: String?, val roles: List<String>) {
    var tokenType = "Bearer"

}
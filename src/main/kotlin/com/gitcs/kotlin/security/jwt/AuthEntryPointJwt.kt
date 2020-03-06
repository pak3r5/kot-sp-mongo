package com.gitcs.kotlin.security.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPointJwt:AuthenticationEntryPoint {
    @Override
    @Throws(Exception::class)
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        if (response != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
        }
    }
}
package com.gitcs.kotlin.security.service

import com.gitcs.kotlin.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors


open class CustomUserDetails(
            id:String?="",
            username:String?="",
            password:String?="",
            authorities: Collection<GrantedAuthority?>?):UserDetails {

    fun build(user:User):CustomUserDetails{
        val authorities: List<GrantedAuthority> = user.roles.stream()
                .map({ role -> SimpleGrantedAuthority(role.name.name) })
                .collect(Collectors.toList())
        return CustomUserDetails(user.id,user.username,user.password,authorities)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = username

    fun getId():String = getId()

    override fun isCredentialsNonExpired(): Boolean =true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean =true

    override fun isAccountNonLocked(): Boolean = true

}
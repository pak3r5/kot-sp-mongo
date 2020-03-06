package com.gitcs.kotlin.model

import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
open class User(
    @Id var id: String? =null,
    @Indexed var username:String?="",
    @Indexed var password:String?="",
    @Indexed var name:String?="",
    @Indexed var lastName:String?="",
    @DBRef var roles:Set<Role> = HashSet()){

    constructor(id:String,username: String?,password: String?) : this(){
        this.id = id
        this.username = username
        this.password = password
    }
}
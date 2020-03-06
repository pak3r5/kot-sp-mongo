package com.gitcs.kotlin.model

import com.gitcs.kotlin.model.enum.EnumRole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "roles")
open class Role (@Id var id:String="",
                 @Indexed var name:EnumRole){
}
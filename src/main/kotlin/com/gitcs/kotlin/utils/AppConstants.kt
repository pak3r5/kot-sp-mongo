package com.gitcs.kotlin.utils

class AppConstants {
    companion object{
        private const val URL_API_BASE="/api"
        private const val URL_API_VERSION="/v1"
        const val URL_BASE= URL_API_BASE + URL_API_VERSION
        const val URL_API_USER = URL_BASE + "/users"
        const val URL_API_ROLE = URL_BASE + "/roles"
    }
}
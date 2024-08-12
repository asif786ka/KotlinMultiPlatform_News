package com.example.kmmnews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
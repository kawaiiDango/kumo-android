package com.arn.kumoexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
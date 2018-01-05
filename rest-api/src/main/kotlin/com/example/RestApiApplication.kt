package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RestApiApplication

fun main(args: Array<String>) {
    SpringApplication.run(RestApiApplication::class.java, *args)
}
package com.example.api

import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class FooApiController {

    fun foo() = FooResponse(foo="FOOOO", now = Instant.now())
}

data class FooResponse(val foo:String, val now:Instant)
package com.example.api.foo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class FooApiController {

    @GetMapping("/api/foo")
    fun foo() = FooResponse(foo = "FOOOO", now = Instant.now())
}

data class FooResponse(val foo: String, val now: Instant)
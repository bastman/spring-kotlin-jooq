package com.example.api.tweeter

import com.example.db.gen.tables.records.TweetRecord
import com.example.domain.TweeterRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class TweeterApiController(private val tweeterRepo: TweeterRepo) {

    @GetMapping("/api/tweeter")
    fun findAll() = tweeterRepo.getAllRecords().map { it.toDto() }
}

data class TweetDto(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val message: String,
        val comment: String?

)

private fun TweetRecord.toDto() = TweetDto(
        id = id,
        version = version,
        createdAt = createdAt.toInstant(),
        modifiedAt = updatedAt.toInstant(),
        message = message,
        comment = comment
)
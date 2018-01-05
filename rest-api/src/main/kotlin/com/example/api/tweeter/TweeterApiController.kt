package com.example.api.tweeter

import com.example.api.tweeter.domain.TweeterRepo
import com.example.db.gen.tables.records.TweetRecord
import com.example.util.sql.toSqlTimestamp
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class TweeterApiController(private val tweeterRepo: TweeterRepo) {

    @GetMapping("/api/tweeter")
    fun findAll() = tweeterRepo.getAllRecords().map { it.toDto() }

    @GetMapping("/api/tweeter/{id}")
    fun getOne(@PathVariable id: UUID) =
            tweeterRepo.requireOneById(id).toDto()

    @PutMapping("/api/tweeter")
    fun createOne(@RequestBody req: CreateTweetRequest): TweetDto {
        val record = req.toRecord()
        tweeterRepo.insert(record)

        return tweeterRepo.requireOneById(record.id).toDto()
    }

    @PostMapping("/api/tweeter/{id}")
    fun updateOne(@PathVariable id: UUID, @RequestBody req: CreateTweetRequest): TweetDto {
        val record = tweeterRepo.requireOneById(id)
        record.apply {
            updatedAt = Instant.now().toSqlTimestamp()
            message = req.message
            comment = req.comment
        }
        tweeterRepo.update(record)

        return tweeterRepo.requireOneById(record.id).toDto()
    }

}

data class CreateTweetRequest(val message: String, val comment: String?)

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

private fun CreateTweetRequest.toRecord(): TweetRecord {
    val now = Instant.now()
    return TweetRecord(UUID.randomUUID(), 0, now.toSqlTimestamp(), now.toSqlTimestamp(), message, comment)
}
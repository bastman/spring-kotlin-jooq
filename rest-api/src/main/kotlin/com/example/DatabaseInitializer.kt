package com.example

import com.example.db.gen.tables.records.TweetRecord
import com.example.domain.TweeterRepo
import com.example.util.sql.toSqlTimestamp
import mu.KLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class DatabaseInitializer(val tweeterRepo: TweeterRepo) : CommandLineRunner {

    override fun run(vararg args: String) {
        logger.info { "START DB INITIALIZER" }

        val now = Instant.now()
        val r: TweetRecord = TweetRecord(
                UUID.randomUUID(), 0, now.toSqlTimestamp(), now.toSqlTimestamp(), "msg $now", "comment $now")

        val x = tweeterRepo.insert(r)
        logger.info { "x=$x" }


    }

    companion object : KLogging()
}

package com.example.api.tweeter.domain

import com.example.api.common.EntityNotFoundException
import com.example.db.gen.tables.Tweet
import com.example.db.gen.tables.records.TweetRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class TweeterRepo(private val dsl: DSLContext) {

    fun getAllRecords(): List<TweetRecord>
            = dsl.select()
            .from(TWEET)
            .fetch()
            .into(TweetRecord::class.java)

    // see: https://stackoverflow.com/questions/45342644/jooq-throws-npe-when-fetchone-is-used
    fun getOneById(id: UUID): TweetRecord?
            = dsl.select()
            .from(TWEET)
            .where(TWEET.ID.eq(id))
            .limit(1)
            .fetch()
            .into(TweetRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: UUID): TweetRecord
            = getOneById(id) ?: throw EntityNotFoundException("TweetRecord NOT FOUND ! (id=$id)")

    fun insert(tweetRecord: TweetRecord) =
            dsl.insertInto(TWEET)
                    .set(tweetRecord)
                    .execute()

    fun update(tweetRecord: TweetRecord) =
            dsl.update(TWEET)
                    .set(tweetRecord)
                    .execute()


    companion object {
        private val TWEET = Tweet.TWEET
    }
}
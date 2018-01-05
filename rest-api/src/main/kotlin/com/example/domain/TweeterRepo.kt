package com.example.domain

import com.example.db.gen.tables.Tweet
import com.example.db.gen.tables.records.TweetRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class TweeterRepo(private val dsl: DSLContext) {

    fun getAllRecords(): List<TweetRecord>
            = dsl.select()
            .from(TWEET)
            .fetch()
            .into(TweetRecord::class.java)


    fun insert(tweetRecord: TweetRecord) =
            dsl.insertInto(TWEET)
                    .set(tweetRecord)
                    .execute()


    companion object {
        private val TWEET = Tweet.TWEET
    }
}
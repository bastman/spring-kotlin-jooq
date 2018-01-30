package com.example.api.bookstore.domain.repo

import com.example.api.common.EntityNotFoundException
import com.example.db.gen.tables.Author
import com.example.db.gen.tables.records.AuthorRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class AuthorRepo(private val dsl: DSLContext) {
    fun getAllRecords(): List<AuthorRecord> = dsl.select()
            .from(AUTHOR)
            .fetch()
            .into(AuthorRecord::class.java)

    // see: https://stackoverflow.com/questions/45342644/jooq-throws-npe-when-fetchone-is-used
    fun getOneById(id: UUID): AuthorRecord? = dsl.select()
            .from(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .limit(1)
            .fetch()
            .into(AuthorRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: UUID): AuthorRecord = getOneById(id)
            ?: throw EntityNotFoundException("AuthorRecord NOT FOUND ! (id=$id)")

    fun insert(authorRecord: AuthorRecord) =
            dsl.insertInto(AUTHOR)
                    .set(authorRecord)
                    .execute()
                    .let { requireOneById(authorRecord.id) }

    fun update(authorRecord: AuthorRecord) =
            dsl.update(AUTHOR)
                    .set(authorRecord)
                    .execute()
                    .let { requireOneById(authorRecord.id) }


    companion object {
        private val AUTHOR = Author.AUTHOR
    }
}
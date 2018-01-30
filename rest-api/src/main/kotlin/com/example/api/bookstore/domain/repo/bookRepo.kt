package com.example.api.bookstore.domain.repo

import com.example.api.common.EntityNotFoundException
import com.example.db.gen.tables.Book
import com.example.db.gen.tables.records.BookRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class BookRepo(private val dsl: DSLContext) {
    fun getAllRecords(): List<BookRecord> = dsl.select()
            .from(BOOK)
            .fetch()
            .into(BookRecord::class.java)

    // see: https://stackoverflow.com/questions/45342644/jooq-throws-npe-when-fetchone-is-used
    fun getOneById(id: UUID): BookRecord? = dsl.select()
            .from(BOOK)
            .where(BOOK.ID.eq(id))
            .limit(1)
            .fetch()
            .into(BookRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: UUID): BookRecord = getOneById(id)
            ?: throw EntityNotFoundException("BookRecord NOT FOUND ! (id=$id)")

    fun insert(bookRecord: BookRecord) =
            dsl.insertInto(BOOK)
                    .set(bookRecord)
                    .execute()
                    .let { requireOneById(bookRecord.id) }

    fun update(bookRecord: BookRecord) =
            dsl.update(BOOK)
                    .set(bookRecord)
                    .execute()
                    .let { requireOneById(bookRecord.id) }


    companion object {
        private val BOOK = Book.BOOK
    }
}
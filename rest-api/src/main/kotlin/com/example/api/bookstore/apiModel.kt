package com.example.api.bookstore

/*
import com.example.api.bookstore.domain.db.Author
import com.example.api.bookstore.domain.db.Book
import com.example.api.bookstore.domain.db.BookStatus
import com.example.api.bookstore.domain.repo.BookRecordJoinAuthorRecord
*/
import com.example.api.bookstore.domain.repo.BookRecordJoinAuthorRecord
import com.example.db.gen.tables.records.AuthorRecord
import com.example.db.gen.tables.records.BookRecord
import com.example.util.sql.toSqlTimestamp
import java.math.BigDecimal
import java.time.Instant
import java.util.*

enum class BookStatus { NEW, PUBLISHED; }
data class AuthorCreateRequest(val name: String)
data class AuthorUpdateRequest(val name: String)
data class BookCreateRequest(val authorId: UUID, val title: String, val status: BookStatus, val price: BigDecimal)
data class BookUpdateRequest(val title: String, val status: BookStatus, val price: BigDecimal)

data class AuthorDto(val id: UUID, val createdAt: Instant, val modifiedAt: Instant, val name: String)
data class BookDto(
        val id: UUID,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val title: String,
        val status: BookStatus,
        val price: BigDecimal,
        val author: AuthorDto
)


fun AuthorRecord.toAuthorDto() = AuthorDto(
        id = id, createdAt = createdAt.toInstant(), modifiedAt = updatedAt.toInstant(), name = name
)


fun AuthorCreateRequest.toAuthorRecord(): AuthorRecord {
    val now = Instant.now()
    return AuthorRecord(
            UUID.randomUUID(),
            0,
            now.toSqlTimestamp(),
            now.toSqlTimestamp(),
            name
    )
}

fun BookCreateRequest.toBookRecord(): BookRecord {
    val now = Instant.now()
    return BookRecord(
            UUID.randomUUID(),
            authorId,
            0,
            now.toSqlTimestamp(),
            now.toSqlTimestamp(),
            title,
            status.name,
            price
    )
}

fun BookRecordJoinAuthorRecord.toBookDto() =
        BookDto(
                id = bookRecord.id,
                createdAt = bookRecord.createdAt.toInstant(),
                modifiedAt = bookRecord.updatedAt.toInstant(),
                title = bookRecord.title,
                status = BookStatus.valueOf(bookRecord.status),
                price = bookRecord.price,
                author = authorRecord.toAuthorDto()
        )

package com.example.api.bookstore


import com.example.api.bookstore.domain.repo.AuthorRepo
import com.example.api.bookstore.domain.repo.BookRepo
import com.example.util.sql.toSqlTimestamp
import mu.KLogging
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class BookStoreApiController(
        private val authorRepo: AuthorRepo,
        private val bookRepo: BookRepo
) {

    @GetMapping("/api/bookstore/authors")
    fun authorsFindAll() =
            authorRepo.getAllRecords()
                    .toList()
                    .map { it.toAuthorDto() }

    @GetMapping("/api/bookstore/authors/{id}")
    fun authorsGetOne(@PathVariable id: UUID) =
            authorRepo.requireOneById(id)
                    .toAuthorDto()

    @PutMapping("/api/bookstore/authors")
    fun authorsCreateOne(@RequestBody req: AuthorCreateRequest) =
            req.toAuthorRecord()
                    .let { authorRepo.insert(it) }
                    .also { logger.info { "Updated Record: $it" } }
                    .toAuthorDto()

    @PostMapping("/api/bookstore/authors/{id}")
    fun authorsUpdateOne(@PathVariable id: UUID, @RequestBody req: AuthorUpdateRequest): AuthorDto = authorRepo.requireOneById(id)
            .apply {
                updatedAt = Instant.now().toSqlTimestamp()
                name = req.name
            }
            .let { authorRepo.update(it) }
            .also { logger.info { "Updated Record: $it" } }
            .toAuthorDto()


    @GetMapping("/api/bookstore/books/{id}")
    fun booksGetOne(@PathVariable id: UUID) =
            bookRepo.requireOneById(id)

    @PutMapping("/api/bookstore/books")
    fun booksCreateOne(@RequestBody req: BookCreateRequest) =
            req.toBookRecord()
                    .let { bookRepo.insert(it) }
                    .also { logger.info { "Updated Record: $it" } }


    @PostMapping("/api/bookstore/books/{id}")
    fun booksUpdateOne(@PathVariable id: UUID, @RequestBody req: BookUpdateRequest) = bookRepo.requireOneById(id)
            .apply {
                updatedAt = Instant.now().toSqlTimestamp()
                title = req.title
                status = req.status.name
                price = req.price
            }
            .let { bookRepo.update(it) }
            .also { logger.info { "Updated Record: $it" } }


    @GetMapping("/api/bookstore/books")
    fun booksFindAll() = bookRepo.findAllBooksJoinAuthor()
            .map { it.toBookDto() }
            .also { logger.info { it } }

    @GetMapping("/api/bookstore/books/summary")
    fun booksFindAllAsSummary() = bookRepo.findAllBooksJoinAuthorAsSummary()

    companion object : KLogging()
}
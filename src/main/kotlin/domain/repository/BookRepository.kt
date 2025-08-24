package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.Book
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.BookWithRental
import kotlinx.datetime.LocalDate

interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>
    fun findWithRental(id: Long): BookWithRental?
    fun register(book: Book): Book
    fun update(id: Long, title: String? = null, author: String? = null, releaseDate: LocalDate? = null): Book
    fun delete(id: Long)

}
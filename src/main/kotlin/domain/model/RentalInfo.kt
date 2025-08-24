package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model

import kotlinx.datetime.LocalDateTime

data class RentalInfo(
    val id: Long,
    val book: Book,
    val user: User,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
    val returnDatetime: LocalDateTime? = null
)
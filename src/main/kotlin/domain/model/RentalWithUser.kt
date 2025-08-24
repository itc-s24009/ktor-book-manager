package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model

import kotlinx.datetime.LocalDateTime

data class RentalWithUser(
    val id: Long,
    val user: User,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
) {
    // Spring Boot 版との互換性のために追加
    val userId: Long
        get() = user.id
}
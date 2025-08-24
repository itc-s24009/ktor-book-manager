package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.application.service

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.RentalInfo
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository.UserRepository
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

private const val RENTAL_TERM_DAYS = 14
private val JST = TimeZone.of("Asia/Tokyo")

class RentalService(
    private val rentalRepository: RentalRepository,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
) {

    fun startRental(bookId: Long, userId: Long): RentalInfo {
        val user = userRepository.find(userId)
            ?: throw IllegalArgumentException("User with id $userId doesn't exist")

        val bookWithRental = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("Book with id $bookId doesn't exist")

        if (bookWithRental.isRental) {
            throw IllegalStateException("Book ${bookWithRental.book.title} is currently on rental.")
        }

        val rentalDatetime = getTimestamp()
        val returnDeadline = getTimestamp(RENTAL_TERM_DAYS)
        val rentalInfo = RentalInfo(
            id = 0,
            book = bookWithRental.book,
            user = user,
            rentalDatetime = rentalDatetime,
            returnDeadline = returnDeadline,
        )
        return rentalRepository.startRental(rentalInfo)
    }

    fun endRental(id: Long, userId: Long): RentalInfo {
        val rentalInfo = rentalRepository.find(id)
            ?: throw IllegalArgumentException("Rental with id $id doesn't exist")

        val user = userRepository.find(userId)
            ?: throw IllegalArgumentException("User with id $userId doesn't exist")

        if (rentalInfo.returnDatetime != null) {
            throw IllegalStateException("Rental with id $id is not currently active.")
        }

        if (rentalInfo.user.id != user.id) {
            throw IllegalStateException("Rental with id $id is not owned by user with id $userId")
        }

        val updateRental = rentalInfo.copy(
            returnDatetime = getTimestamp()
        )
        return rentalRepository.endRental(updateRental)
    }

    @OptIn(ExperimentalTime::class)
    private fun getTimestamp(delta: Int = 0): LocalDateTime {
        val now = Clock.System.now()
        return now.plus(delta.days).toLocalDateTime(JST)
    }
}
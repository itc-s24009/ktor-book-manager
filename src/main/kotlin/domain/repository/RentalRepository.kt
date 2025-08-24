package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.RentalInfo

interface RentalRepository {
    fun find(id: Long): RentalInfo?
    fun startRental(rental: RentalInfo): RentalInfo
    fun endRental(rental: RentalInfo): RentalInfo
}
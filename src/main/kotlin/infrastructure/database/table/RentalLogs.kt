package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.table

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.datetime

object RentalLogs : LongIdTable("rental_logs") {
    val book = reference("book_id", Books)
    val user = reference("user_id", Users)
    val rentalDatetime = datetime("rental_datetime")
    val returnDeadline = datetime("return_deadline")
    val returnDatetime = datetime("return_datetime").nullable()
}
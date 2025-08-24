package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.entity

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.table.Books
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.table.RentalLogs
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ImmutableEntityClass
import org.jetbrains.exposed.v1.dao.LongEntity

class BookWithRentalLogEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : ImmutableEntityClass<Long, BookWithRentalLogEntity>(Books)

    val title by Books.title
    val author by Books.author
    val releaseDate by Books.releaseDate

    val rentalLogs by RentalLogEntity referrersOn RentalLogs.book orderBy RentalLogs.rentalDatetime

    val currentLog: RentalLogEntity?
        get() = rentalLogs.firstOrNull { it.returnDatetime == null }
}
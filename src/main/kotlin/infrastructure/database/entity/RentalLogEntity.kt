package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.entity

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.infrastructure.database.table.RentalLogs
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass


class RentalLogEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RentalLogEntity>(RentalLogs)

    var book by BookEntity referencedOn RentalLogs.book
    var user by UserEntity referencedOn RentalLogs.user
    var rentalDatetime by RentalLogs.rentalDatetime
    var returnDatetime by RentalLogs.returnDatetime
    var returnDeadline by RentalLogs.returnDeadline
}
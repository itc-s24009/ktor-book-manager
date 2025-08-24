package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.controller

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.plugins.di.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.sessions.*
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.application.service.RentalService
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.UserPrincipal
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.DeleteRentalEndResponse
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.PostRentalStartRequest
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.PostRentalStartResponse

fun Route.rentalRouting() {
    val service: RentalService by application.dependencies


    post<Rental.Start> { params ->
        val body = call.receive<PostRentalStartRequest>()
        val user = call.sessions.get<UserPrincipal>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val rental = service.startRental(body.bookId, user.id)
        call.respond(
            HttpStatusCode.OK, PostRentalStartResponse(
                rental.rentalDatetime, rental.returnDeadline
            )
        )
    }
    delete<Rental.End> { params ->
        val user = call.sessions.get<UserPrincipal>() ?: return@delete call.respond(HttpStatusCode.BadRequest)
        val rental = service.endRental(params.id, user.id)
        call.respond(HttpStatusCode.OK, rental.run {
            DeleteRentalEndResponse(
                rentalDatetime,
                returnDeadline,
                returnDatetime ?: throw IllegalStateException("What, return_datetime is null")
            )
        }
        )
    }
}


@Resource("/rental")
class Rental {
    @Resource("/start")
    class Start(val rental: Rental = Rental())

    @Resource("/end/{id}")
    class End(val rental: Rental = Rental(), val id: Long)
}
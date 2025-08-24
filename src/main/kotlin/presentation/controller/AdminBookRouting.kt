package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.controller

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.plugins.di.*
import io.ktor.server.request.*
import io.ktor.server.resources.delete
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.application.service.AdminBookService
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.Book
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.PostBookRegisterRequest
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.BookInfoResponse
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.form.PutBookUpdateRequest

fun Route.adminBookRouting() {
    val service: AdminBookService by application.dependencies

    post<AdminBook.Register> { params ->
        val body = call.receive<PostBookRegisterRequest>()
        val newBook = service.register(body.run {
            Book(
                id = 0, // IDは自動生成されるため、0を指定
                title = title,
                author = author,
                releaseDate = releaseDate,
            )
        })
        call.respond(HttpStatusCode.Created, newBook.run {
            BookInfoResponse(id, title, author, releaseDate)
        })
    }

    // 。
    put<AdminBook.Update> { params ->
        val body = call.receive<PutBookUpdateRequest>()
        val updatedBook = body.run {
            service.update(id, title, author, releaseDate)
        }
        call.respond(HttpStatusCode.OK, updatedBook.run {
            BookInfoResponse(id, title, author, releaseDate)
        })
    }

    delete<AdminBook.Delete> { params ->
        service.delete(params.id)
        call.respond(HttpStatusCode.NoContent)
    }
}

@Resource("/admin/book")
class AdminBook {
    @Resource("/register")
    class Register(val adminBook: AdminBook = AdminBook())

    @Resource("/update")
    class Update(val adminBook: AdminBook = AdminBook())

    @Resource("/delete/{id}")
    class Delete(val adminBook: AdminBook = AdminBook(), val id: Long)
}
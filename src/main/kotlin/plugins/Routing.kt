package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.controller.bookRouting
import io.ktor.server.auth.AuthenticationStrategy
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.controller.adminBookRouting
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.presentation.controller.rentalRouting




fun Application.configureRouting() {
    install(Resources)
    routing {
        authenticate("user-session" ,strategy = AuthenticationStrategy.Required) {
            bookRouting()
            rentalRouting()
            authenticate("admin-session", strategy = AuthenticationStrategy.Required) {
                adminBookRouting()
            }
        }
    }
}
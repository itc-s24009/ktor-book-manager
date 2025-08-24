package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.application.service

import de.mkammerer.argon2.Argon2Factory
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.UserPrincipal
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository.UserRepository

class AuthenticationService(
    private val repository: UserRepository
) {
    private val argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id
    )

    fun authenticate(email: String, password: String): UserPrincipal? {
        val user = repository.find(email) ?: return null

        return if (argon2.verify(user.password, password.toCharArray())) {
            user
        } else {
            null
        }
    }
}
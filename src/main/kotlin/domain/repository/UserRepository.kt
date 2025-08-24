package jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.repository

import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.User
import jp.ac.it_college.std.s24009.kotlin.ktor.book.manager.domain.model.UserPrincipal

interface UserRepository {
    fun find(email: String): UserPrincipal?
    fun find(id: Long): User?
}
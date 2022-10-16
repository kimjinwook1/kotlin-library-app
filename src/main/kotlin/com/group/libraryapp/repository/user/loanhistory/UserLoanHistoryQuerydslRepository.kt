package com.group.libraryapp.repository.user.loanhistory

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserLoanHistoryQuerydslRepository(
    private val querydslFactory: JPAQueryFactory
) {

    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return querydslFactory.selectFrom(userLoanHistory)
            .where(
                userLoanHistory.bookName.eq(bookName),
                status?.let { userLoanHistory.status.eq(status) }
            )
            .fetchFirst()
    }

    fun count(status: UserLoanStatus): Long {
        return querydslFactory.select(userLoanHistory.count())
            .from(userLoanHistory)
            .where(
                userLoanHistory.status.eq(status)
            ).fetchOne() ?: 0L

    }

}

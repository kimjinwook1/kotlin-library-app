package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?,
) {
    // 정적 팩토리 메서드 사용
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                name = user.name,
                age = user.age,
            )
        }
    }

//    부생성자를 사용하는 방법
//    constructor(user: User): this(
//        id = user.id!!, <- !! 들어간 이유는 처음에 null이 들어갈 수 있기 때문이다.
//        name = user.name,
//        age = user.age,
//    )
}

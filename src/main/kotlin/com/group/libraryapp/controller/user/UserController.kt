package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {

    @PostMapping
    fun saveUser(@RequestBody request: UserCreateRequest) {
        userService.saveUser(request)
    }

    @GetMapping
    fun getUsers(): List<UserResponse> {
        return userService.getUsers()
    }

    @PutMapping
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @DeleteMapping
    fun deleteUser(@RequestParam name: String) {
        // @RequestParam을 사용할 때 타입을 nullable 하게 사용하면 required = false 로 변경한다.
        userService.deleteUser(name)
    }

    @GetMapping("/loan")
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userService.getUserLoanHistories()
    }
}

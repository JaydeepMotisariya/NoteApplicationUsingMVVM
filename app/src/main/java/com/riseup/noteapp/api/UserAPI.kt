package com.riseup.noteapp.api

import com.riseup.noteapp.models.UserRequest
import com.riseup.noteapp.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("/users/signup")
    suspend fun signUp(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/users/signin")
    suspend fun signIn(@Body userRequest: UserRequest) : Response<UserResponse>
}
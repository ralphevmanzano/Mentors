package com.ralphevmanzano.apps.core.data.remote

import com.ralphevmanzano.apps.domain.model.Details
import com.ralphevmanzano.apps.domain.model.Mentor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MentorService {

    @GET("users")
    suspend fun getMentors(): Response<List<Mentor>>

    @GET("users/{login}")
    suspend fun getMentorDetails(@Path("login") login: String): Response<Details>
}
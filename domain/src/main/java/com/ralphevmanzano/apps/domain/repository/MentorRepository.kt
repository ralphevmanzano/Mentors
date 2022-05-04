package com.ralphevmanzano.apps.domain.repository

import com.ralphevmanzano.apps.domain.model.Details
import com.ralphevmanzano.apps.domain.model.Mentor
import com.ralphevmanzano.apps.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MentorRepository {
    suspend fun getMentors(): Flow<Resource<List<Mentor>>>
    suspend fun getMentorDetails(login: String): Flow<Resource<Details>>
}
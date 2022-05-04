package com.ralphevmanzano.apps.core.data.datasource

import com.ralphevmanzano.apps.domain.model.Details
import com.ralphevmanzano.apps.common.utils.resultFlow
import com.ralphevmanzano.apps.core.data.remote.MentorService
import com.ralphevmanzano.apps.domain.datasource.MentorRemoteDataSource
import com.ralphevmanzano.apps.domain.model.Mentor
import com.ralphevmanzano.apps.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MentorRemoteDataSourceImpl @Inject constructor(
    private val mentorService: MentorService,
    private val dispatcher: CoroutineDispatcher
): MentorRemoteDataSource {

    override suspend fun getMentors(): Flow<Resource<List<Mentor>>> = resultFlow(dispatcher) {
        mentorService.getMentors()
    }

    override suspend fun getMentorDetails(login: String): Flow<Resource<Details>> = resultFlow(dispatcher) {
        mentorService.getMentorDetails(login)
    }
}
package com.ralphevmanzano.apps.core.data.repository

import com.ralphevmanzano.apps.domain.datasource.MentorRemoteDataSource
import com.ralphevmanzano.apps.domain.repository.MentorRepository
import javax.inject.Inject

class MentorRepositoryImpl @Inject constructor(
    private val mentorRemoteDataSource: MentorRemoteDataSource,
) : MentorRepository {

    // TODO: if local data source is required, logic can be added here whether to fetch from api or local

    override suspend fun getMentors() = mentorRemoteDataSource.getMentors()

    override suspend fun getMentorDetails(login: String) = mentorRemoteDataSource.getMentorDetails(login)
}
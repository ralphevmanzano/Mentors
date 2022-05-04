package com.ralphevmanzano.apps.core.di

import com.ralphevmanzano.apps.core.data.datasource.MentorRemoteDataSourceImpl
import com.ralphevmanzano.apps.core.data.remote.MentorService
import com.ralphevmanzano.apps.core.data.repository.MentorRepositoryImpl
import com.ralphevmanzano.apps.domain.datasource.MentorRemoteDataSource
import com.ralphevmanzano.apps.domain.repository.MentorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MentorSingletonModule {

    @Provides
    @Singleton
    fun provideMentorRemoteDataSource(
        mentorService: MentorService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): MentorRemoteDataSource {
        return MentorRemoteDataSourceImpl(mentorService, dispatcher)
    }

    @Provides
    @Singleton
    fun provideMentorRepository(
        mentorRemoteDataSource: MentorRemoteDataSource
    ): MentorRepository {
        return MentorRepositoryImpl(mentorRemoteDataSource)
    }
}
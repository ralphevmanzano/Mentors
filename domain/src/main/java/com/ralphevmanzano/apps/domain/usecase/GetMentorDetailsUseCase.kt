package com.ralphevmanzano.apps.domain.usecase

import com.ralphevmanzano.apps.domain.repository.MentorRepository
import javax.inject.Inject

class GetMentorDetailsUseCase @Inject constructor(private val mentorRepository: MentorRepository) {
    suspend operator fun invoke(login: String) = mentorRepository.getMentorDetails(login)
}
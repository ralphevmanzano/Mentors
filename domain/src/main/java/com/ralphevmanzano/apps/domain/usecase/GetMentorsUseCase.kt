package com.ralphevmanzano.apps.domain.usecase

import com.ralphevmanzano.apps.domain.repository.MentorRepository
import javax.inject.Inject

class GetMentorsUseCase @Inject constructor(private val mentorsRepository: MentorRepository) {
    suspend operator fun invoke() = mentorsRepository.getMentors()
}
package com.ralphevmanzano.apps.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.apps.domain.model.Mentor
import com.ralphevmanzano.apps.domain.model.Resource
import com.ralphevmanzano.apps.domain.usecase.GetMentorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMentorsUseCase: GetMentorsUseCase
): ViewModel() {

    private val _mentorsResult = MutableLiveData<Resource<List<Mentor>>>()
    val mentorsResult: LiveData<Resource<List<Mentor>>> = _mentorsResult

    init {
        viewModelScope.launch {
            getMentorsUseCase().collect {
                _mentorsResult.value = it
            }
        }
    }
}
package com.ralphevmanzano.apps.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.apps.common.extensions.formatDate
import com.ralphevmanzano.apps.domain.model.Details
import com.ralphevmanzano.apps.domain.model.Resource
import com.ralphevmanzano.apps.domain.model.TimeSlot
import com.ralphevmanzano.apps.domain.usecase.GetMentorDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMentorDetailsUseCase: GetMentorDetailsUseCase
): ViewModel() {

    private val _detailsResult = MutableLiveData<Resource<Details>>()
    val detailsResult: LiveData<Resource<Details>> = _detailsResult

    private val _dateStr = MutableLiveData<String>()
    val dateStr: LiveData<String> = _dateStr

    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlots: LiveData<List<TimeSlot>> = _timeSlots

    private val _canBook = MutableLiveData(false)
    val canBook: LiveData<Boolean> = _canBook

    var selectedDate: Calendar = Calendar.getInstance()
    var currentDate: Calendar = Calendar.getInstance()

    var selectedTimeSlot: TimeSlot? = null

    init {
        formatSelectedDate()
    }

    private fun formatSelectedDate() {
        _dateStr.value = selectedDate.time.formatDate()
        refreshTimeSlots()
    }

    fun getMentorDetails(login: String) {
        viewModelScope.launch {
            getMentorDetailsUseCase(login).collect {
                _detailsResult.value = it
            }
        }
    }

    fun prevDate() {
        selectedDate.add(Calendar.DAY_OF_YEAR, -1)
        formatSelectedDate()
        _canBook.value = false
    }

    fun nextDate() {
        selectedDate.add(Calendar.DAY_OF_YEAR, 1)
        formatSelectedDate()
        _canBook.value = false
    }

    fun setTimeSlot(timeSlot: TimeSlot) {
        selectedTimeSlot = timeSlot
        _canBook.value = true
    }

    private fun refreshTimeSlots() {
        val list = listOf(
            TimeSlot("8:00 am", 8),
            TimeSlot("8:30 am", 8, 30),
            TimeSlot("9:00 am", 9),
            TimeSlot("9:30 am", 9, 30),
            TimeSlot("10:00 am", 10),
            TimeSlot("10:30 am", 10, 30),
            TimeSlot("11:00 am", 11),
            TimeSlot("11:30 am", 11, 30),
            TimeSlot("1:00 pm", 13),
            TimeSlot("1:30 pm", 13, 30),
            TimeSlot("2:00 pm", 14),
            TimeSlot("2:30 pm", 14, 30),
            TimeSlot("3:00 pm", 15),
            TimeSlot("3:30 pm", 15, 30),
        )
        _timeSlots.value = list
    }
}
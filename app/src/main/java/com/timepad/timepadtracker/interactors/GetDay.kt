package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.DayRepository

class GetDay(private val dayRepository: DayRepository) {
    suspend operator fun invoke(day: Int) = dayRepository.getDay(day)
}
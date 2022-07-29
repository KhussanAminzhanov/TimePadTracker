package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.DayRepository

class GetAllDays(private val dayRepository: DayRepository) {
    suspend operator fun invoke() = dayRepository.getAll()
}
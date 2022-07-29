package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.DayRepository
import com.timepad.timepadtracker.domain.Day

class AddDay(private val dayRepository: DayRepository) {
    suspend operator fun invoke(day: Day) = dayRepository.addDay(day)
}
package com.kingtech.conferenceapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingtech.conferenceapplication.data.ConferenceRepository
import com.kingtech.conferenceapplication.modules.attendants.AttendantViewModel
import com.kingtech.conferenceapplication.modules.speaker.SpeakersViewModel

class ViewModelFactoryProvider(
    private val conferenceRepository: ConferenceRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AttendantViewModel::class.java)) {
            AttendantViewModel(conferenceRepository) as T
        } else {
            SpeakersViewModel(conferenceRepository) as T
        }
    }
}
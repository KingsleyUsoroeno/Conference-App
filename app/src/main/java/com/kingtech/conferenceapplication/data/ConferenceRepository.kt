package com.kingtech.conferenceapplication.data

import com.kingtech.conferenceapplication.data.local.entities.Atteendee
import com.kingtech.conferenceapplication.data.local.entities.Speaker
import com.kingtech.conferenceapplication.data.remote.ConferenceApi

class ConferenceRepository(private val conferenceApi: ConferenceApi) {

    suspend fun registerUser(speaker: Speaker) =
        conferenceApi.registerSpeaker(speaker)

    suspend fun registerAttendant(atteendee: Atteendee) =
        conferenceApi.registerAttendee(atteendee)
}
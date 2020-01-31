package com.kingtech.conferenceapplication.data.remote

import com.kingtech.conferenceapplication.data.BaseResponse
import com.kingtech.conferenceapplication.data.local.entities.Atteendee
import com.kingtech.conferenceapplication.data.local.entities.Speaker
import com.kingtech.conferenceapplication.data.remote.models.Attendants
import com.kingtech.conferenceapplication.data.remote.models.Speakers
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConferenceApi {

    @GET("attendee/all")
    suspend fun getAllAttendees(): Response<BaseResponse<Attendants>>

    @POST("attendee/")
    suspend fun registerAttendee(@Body atteendee: Atteendee): Response<ResponseBody>

    @POST("speakers/all")
    suspend fun getAllSpeakers(): Response<BaseResponse<Speakers>>

    @POST("speakers/add")
    suspend fun registerSpeaker(@Body speaker: Speaker): Response<ResponseBody>
}
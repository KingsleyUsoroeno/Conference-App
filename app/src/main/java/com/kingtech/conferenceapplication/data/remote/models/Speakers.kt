package com.kingtech.conferenceapplication.data.remote.models


import com.google.gson.annotations.SerializedName

data class Speakers(
    @SerializedName("attendee")
    val attendee: List<Attendants>,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("topic")
    val topic: String,
    @SerializedName("__v")
    val v: Int
)
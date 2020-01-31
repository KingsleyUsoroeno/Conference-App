package com.kingtech.conferenceapplication.data.local.entities


import com.google.gson.annotations.SerializedName

data class Speaker(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("topic")
    val topic: String
)
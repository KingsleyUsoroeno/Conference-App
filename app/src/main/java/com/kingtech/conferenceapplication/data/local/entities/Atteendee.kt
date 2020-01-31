package com.kingtech.conferenceapplication.data.local.entities


import com.google.gson.annotations.SerializedName

data class Atteendee(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String
)
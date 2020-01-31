package com.kingtech.conferenceapplication.data.remote.models


import com.google.gson.annotations.SerializedName

data class Attendants(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("__v")
    val v: Int
)
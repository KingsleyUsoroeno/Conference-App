package com.kingtech.conferenceapplication.data

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(t: T) {

    @SerializedName("data")
    var response = t

}
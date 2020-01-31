package com.kingtech.conferenceapplication.modules.speaker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingtech.conferenceapplication.data.ConferenceRepository
import com.kingtech.conferenceapplication.data.local.entities.Speaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpeakersViewModel(private val conferenceRepository: ConferenceRepository) : ViewModel() {

    val nameMutableLiveData = MutableLiveData<String>("")
    val topicMutableLiveData = MutableLiveData<String>("")
    val emailMutableLiveData = MutableLiveData<String>("")
    val passwordMutableLiveData = MutableLiveData<String>("")
    val durationMutableLiveData = MutableLiveData<String>("")

    val nameErrorMutableLiveData = MutableLiveData<String>("")
    val topicErrorMutableLiveData = MutableLiveData<String>("")
    val emailErrorMutableLiveData = MutableLiveData<String>("")
    val passwordErrorMutableLiveData = MutableLiveData<String>("")
    val durationErrorMutableLiveData = MutableLiveData<String>("")

    private val loadingStateMutableLiveData = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = loadingStateMutableLiveData

    private val successStateMutableLiveData = MutableLiveData<Boolean>()
    val successState: LiveData<Boolean>
        get() = successStateMutableLiveData

    private val errorStateMutableLiveData = MutableLiveData<String>()
    val errorState: LiveData<String>
        get() = errorStateMutableLiveData


    fun registerSpeaker() {
        if (!validateFields(
                nameMutableLiveData,
                nameErrorMutableLiveData,
                "Name"
            ) || !validateFields(topicMutableLiveData, topicErrorMutableLiveData, "Topic")
            || !validateFields(
                emailMutableLiveData,
                emailErrorMutableLiveData,
                "Email"
            ) || !validateFields(passwordMutableLiveData, passwordErrorMutableLiveData, "Password")
            || !validateFields(durationMutableLiveData, durationErrorMutableLiveData, "Duration")
        ) {
            return
        }

        // registerAttendee
        val name = nameMutableLiveData.value!!
        val topic = topicMutableLiveData.value!!
        val email = emailMutableLiveData.value!!
        val password = passwordMutableLiveData.value!!
        val duration = durationMutableLiveData.value!!.toInt()

        val speaker = Speaker(duration, email, name, password, topic)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    loadingStateMutableLiveData.postValue(true)
                    Log.i("AttendantVM", "making network call")
                    val res = conferenceRepository.registerUser(speaker)
                    if (res.isSuccessful) {
                        loadingStateMutableLiveData.postValue(false)
                        successStateMutableLiveData.postValue(true)
                        Log.i("speakerVm", "response is ${res.body()?.string()}")
                        clear()
                    } else {
                        loadingStateMutableLiveData.postValue(false)
                        Log.i("speakerVm", "error msg is ${res.errorBody()?.string()}")
                        errorStateMutableLiveData.postValue(res.errorBody()?.string())
                        successStateMutableLiveData.postValue(false)
                    }
                } catch (e: Throwable) {
                    loadingStateMutableLiveData.postValue(false)
                    Log.i("speakerVm", "error msg is ${e.message}")
                    errorStateMutableLiveData.postValue(e.message)
                    successStateMutableLiveData.postValue(false)
                }
            }
        }
    }

    fun clear() {
        nameMutableLiveData.value = ""
        topicMutableLiveData.value = ""
        emailMutableLiveData.value = ""
        passwordMutableLiveData.value = ""
        durationMutableLiveData.value = ""
    }

    private fun validateFields(
        key: MutableLiveData<String>,
        errorLiveData: MutableLiveData<String>,
        field: String
    ): Boolean {
        return if (key.value!!.isEmpty()) {
            errorLiveData.value = "$field is required"
            false
        } else {
            errorLiveData.value = null
            true
        }
    }

}

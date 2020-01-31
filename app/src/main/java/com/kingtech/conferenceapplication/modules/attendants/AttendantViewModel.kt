package com.kingtech.conferenceapplication.modules.attendants

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingtech.conferenceapplication.data.ConferenceRepository
import com.kingtech.conferenceapplication.data.local.entities.Atteendee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttendantViewModel(private val conferenceRepository: ConferenceRepository) : ViewModel() {

    val firstNameMutableLiveData = MutableLiveData<String>("")
    val lastNameMutableLiveData = MutableLiveData<String>("")
    val emailMutableLiveData = MutableLiveData<String>("")
    val passwordMutableLiveData = MutableLiveData<String>("")

    val firstNameErrorMutableLiveData = MutableLiveData<String>("")
    val lastNameErrorMutableLiveData = MutableLiveData<String>("")
    val emailErrorMutableLiveData = MutableLiveData<String>("")
    val errorPasswordErrorMutableLiveData = MutableLiveData<String>("")

    private val loadingStateMutableLiveData = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = loadingStateMutableLiveData

    private val successStateMutableLiveData = MutableLiveData<Boolean>()
    val successState: LiveData<Boolean>
        get() = successStateMutableLiveData

    private val errorStateMutableLiveData = MutableLiveData<String>()
    val errorState: LiveData<String>
        get() = errorStateMutableLiveData

    fun registerAttendee() {
        if (!validateFields(
                firstNameMutableLiveData,
                firstNameErrorMutableLiveData,
                "First Name"
            ) || !validateFields(lastNameMutableLiveData, lastNameErrorMutableLiveData, "Last Name")
            || !validateFields(
                emailMutableLiveData,
                emailErrorMutableLiveData,
                "Email"
            ) || !validateFields(
                passwordMutableLiveData,
                errorPasswordErrorMutableLiveData,
                "Password"
            )
        ) {
            return
        }
        // registerAttendee
        val firstName = firstNameMutableLiveData.value!!
        val lastName = lastNameMutableLiveData.value!!
        val email = emailMutableLiveData.value!!
        val password = passwordMutableLiveData.value!!

        val attendant = Atteendee(email, firstName, lastName, password)
        viewModelScope.launch(Dispatchers.Main) {
                try {
                    Log.i("AttendantVM", "making network call")
                    loadingStateMutableLiveData.postValue(true)
                    val res = conferenceRepository.registerAttendant(attendant)
                    if (res.isSuccessful) {
                        loadingStateMutableLiveData.postValue(false)
                        successStateMutableLiveData.postValue(true)
                        Log.i("AttendantVm", "response is ${res.body()?.string()}")
                        clear()
                    } else {
                        loadingStateMutableLiveData.postValue(false)
                        successStateMutableLiveData.postValue(false)
                        errorStateMutableLiveData.postValue(res.errorBody()?.string())
                        Log.i("AttendantVm", "error msg is ${res.errorBody()?.string()}")
                    }
                } catch (e: Throwable) {
                    loadingStateMutableLiveData.postValue(false)
                    successStateMutableLiveData.postValue(false)
                    errorStateMutableLiveData.postValue(e.message)
                    Log.i("AttendantVm", "error msg is ${e.message}")
                }
        }
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

    fun clear() {
        firstNameMutableLiveData.value = ""
        lastNameMutableLiveData.value = ""
        emailMutableLiveData.value = ""
        passwordMutableLiveData.value = ""
    }
}
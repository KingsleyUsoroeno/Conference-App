package com.kingtech.conferenceapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {

    private val stateCountMutableLiveData = MutableLiveData<Int>()

    val stateCountLiveData: LiveData<Int>
        get() = stateCountMutableLiveData

    private var stateCount = 0

    fun navigateOnStateClicked(count: Int) {
        this.stateCount = count
        Log.i("ActivityViewModel", "state value is $stateCount")
        stateCountMutableLiveData.value = stateCount
    }
}
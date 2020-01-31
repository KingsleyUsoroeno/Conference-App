package com.kingtech.conferenceapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.kingtech.conferenceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        viewBinding.activityViewModel = activityViewModel
        viewBinding.lifecycleOwner = this

        activityViewModel.stateCountLiveData.observe(this, Observer { stateCount ->
            when (stateCount) {
                0 -> navController.navigate(R.id.attendantFragment)
                1 -> navController.navigate(R.id.speakersFragment)
            }
        })
    }
}

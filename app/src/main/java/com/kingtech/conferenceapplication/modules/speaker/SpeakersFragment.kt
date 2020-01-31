package com.kingtech.conferenceapplication.modules.speaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kingtech.conferenceapplication.R
import com.kingtech.conferenceapplication.ViewModelFactoryProvider
import com.kingtech.conferenceapplication.data.ConferenceRepository
import com.kingtech.conferenceapplication.data.remote.RetrofitClient
import com.kingtech.conferenceapplication.databinding.SpeakersFragmentBinding

class SpeakersFragment : Fragment() {

    companion object {
        fun newInstance() = SpeakersFragment()
    }

    private lateinit var viewModel: SpeakersViewModel
    private lateinit var viewBinding: SpeakersFragmentBinding
    private lateinit var spinner: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.speakers_fragment, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactoryProvider =
            ViewModelFactoryProvider(ConferenceRepository((RetrofitClient.service())))
        viewModel =
            ViewModelProviders.of(this, viewModelFactoryProvider).get(SpeakersViewModel::class.java)
        viewBinding.speakerVm = viewModel
        viewBinding.lifecycleOwner = this
        spinner = getSpinnerDialog()
        initObservers()
    }

    private fun initObservers() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) spinner.show() else spinner.dismiss()
        })
        viewModel.successState.observe(viewLifecycleOwner, Observer { isSuccessful ->
            if (isSuccessful) {
                spinner.dismiss()
                Toast.makeText(
                    this.requireContext(),
                    "Speaker registered successfully ",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
        viewModel.errorState.observe(viewLifecycleOwner, Observer { error ->
            if (error.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Operation Failed", Toast.LENGTH_LONG).show()

            }
            Toast.makeText(requireContext(), "Encountered an issue $error", Toast.LENGTH_LONG)
                .show()
        })
    }

    private fun getSpinnerDialog(): AlertDialog {
        val dialog = AlertDialog.Builder(this.requireContext())
        val dialogView =
            LayoutInflater.from(this.requireContext()).inflate(R.layout.layout_loading, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        return dialog.create()
    }
}

package com.kingtech.conferenceapplication.modules.attendants

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
import com.kingtech.conferenceapplication.databinding.AttendantFragmentBinding

class AttendantFragment : Fragment() {

    companion object {
        fun newInstance() = AttendantFragment()
    }

    private lateinit var viewModel: AttendantViewModel
    private lateinit var viewBinding: AttendantFragmentBinding
    private lateinit var spinner: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.attendant_fragment, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factoryProvider =
            ViewModelFactoryProvider(ConferenceRepository((RetrofitClient.service())))
        viewModel = ViewModelProviders.of(this, factoryProvider).get(AttendantViewModel::class.java)
        viewBinding.attendantVm = viewModel
        viewBinding.lifecycleOwner = this
        spinner = getSpinnerDialog()
        initObservers()
    }


    // viewModel
    private fun initObservers() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) spinner.show() else spinner.dismiss()

        })
        viewModel.successState.observe(viewLifecycleOwner, Observer { isSuccessful ->
            if (isSuccessful && spinner.isShowing) spinner.dismiss()
            Toast.makeText(
                this.requireContext(),
                "Attendant registered successfully ",
                Toast.LENGTH_LONG
            ).show()
        })
        viewModel.errorState.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
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

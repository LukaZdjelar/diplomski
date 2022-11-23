package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentRegistrationBinding
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegistrationFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var registrationBinding : FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        registrationBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            registrationFragment = this@RegistrationFragment
        }

        buttonRegister.setOnClickListener {
            val job = CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertUser(mainViewModel.newUser.value!!)
            }

            runBlocking { job.join() }
            if (Navigation.findNavController(view).previousBackStackEntry?.destination?.id != R.id.loginFragment){
                mainViewModel.setUser(mainViewModel.newUser.value!!)
            }
            activity?.onBackPressed()
        }
    }
}
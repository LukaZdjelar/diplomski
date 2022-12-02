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
import com.example.diplomski_android.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegistrationFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            registrationFragment = this@RegistrationFragment
        }

        binding.buttonRegister.setOnClickListener {
            val job = CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertUser(mainViewModel.newUser.value!!)
            }

            runBlocking { job.join() }
            if (Navigation.findNavController(view).previousBackStackEntry?.destination?.id != R.id.loginFragment){
                mainViewModel.setUser(mainViewModel.newUser.value!!)
            }
            mainViewModel.setNewUser(User())
            activity?.onBackPressed()
        }
    }
}
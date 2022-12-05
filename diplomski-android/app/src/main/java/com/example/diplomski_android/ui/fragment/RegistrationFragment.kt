package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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

        textChangeListener()
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
            if (validateOnConfirm()){
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

    fun textChangeListener(){
        binding.registrationName.doOnTextChanged { _, _, _, _ ->
            binding.registrationNameContainer.helperText = validateName()
        }
        binding.registrationEmail.doOnTextChanged { _, _, _, _ ->
            binding.registrationEmailContainer.helperText = validateEmail()
        }
        binding.registrationPassword.doOnTextChanged { _, _, _, _ ->
            binding.registrationPasswordContainer.helperText = validatePassword()
        }
        binding.registrationConfirmPassword.doOnTextChanged { _, _, _, _ ->
            binding.registrationConfirmPasswordContairner.helperText = validateConfirmPassword()
        }
    }

    fun validateName(): String?{
        val name = binding.registrationName.text.toString()
        if (name == ""){
            return "Required"
        }
        return null
    }

    fun validateEmail(): String?{
        val email = binding.registrationEmail.text.toString()
        if (email == ""){
            return "Required"
        }
        return null
    }

    fun validatePassword(): String?{
        val password = binding.registrationPassword.text.toString()
        if (password == ""){
            return "Required"
        }
        if (password != binding.registrationConfirmPassword.text.toString()){
            binding.registrationConfirmPasswordContairner.helperText = "Passwords don't match"
        }
        if (password == binding.registrationConfirmPassword.text.toString()){
            binding.registrationConfirmPasswordContairner.helperText = null
        }
        return null
    }

    fun validateConfirmPassword(): String?{
        val confirmPassword = binding.registrationConfirmPassword.text.toString()
        if (confirmPassword == ""){
            return "Required"
        }
        if (confirmPassword != binding.registrationPassword.text.toString()){
            return "Passwords don't match"
        }
        return null
    }

    fun validateOnConfirm(): Boolean{
        binding.registrationNameContainer.helperText = validateName()
        binding.registrationEmailContainer.helperText = validateEmail()
        binding.registrationPasswordContainer.helperText = validatePassword()
        binding.registrationConfirmPasswordContairner.helperText = validateConfirmPassword()

        if (binding.registrationNameContainer.helperText == null &&
            binding.registrationEmailContainer.helperText == null &&
            binding.registrationPasswordContainer.helperText == null &&
            binding.registrationConfirmPasswordContairner.helperText == null){
            return true
        }

        return false
    }
}
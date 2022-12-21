package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentRegistrationBinding
import com.example.diplomski_android.model.User
import com.example.diplomski_android.viewmodel.MainViewModel
import com.example.diplomski_android.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val registrationViewModel: RegistrationViewModel by viewModels()
    private lateinit var binding: FragmentRegistrationBinding
    private val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"

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
            registrationViewModel = registrationViewModel
            registrationFragment = this@RegistrationFragment
        }

        binding.buttonRegister.setOnClickListener {
            if (validateOnConfirm()){
                CoroutineScope(Dispatchers.IO).launch {
                    registrationViewModel.newUser.value?.let { user ->
                        mainViewModel.insertUser(user)
                    }
                }

                lifecycleScope.launch {
                    if (Navigation.findNavController(view).previousBackStackEntry?.destination?.id != R.id.loginFragment){
                        registrationViewModel.newUser.value?.let { user ->
                            mainViewModel.setUser(user)
                        }
                    }
                    registrationViewModel.setNewUser(User())
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun textChangeListener() {
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
            binding.registrationConfirmPasswordContainer.helperText = validateConfirmPassword()
        }
    }

    private fun validateName(): String? {
        val name = binding.registrationName.text.toString()
        if (name == "") {
            return "Required"
        }
        return null
    }

    private fun validateEmail(): String?{
        val email = binding.registrationEmail.text.toString()
        if (email == ""){
            return "Required"
        }
        if (!Regex(emailRegex).containsMatchIn(email)){
            return "Invalid email address"
        }
        return null
    }

    private fun validatePassword(): String? {
        val password = binding.registrationPassword.text.toString()
        if (password == "") {
            return "Required"
        }
        if (password.length < 6) {
            return "Password must be at least 6 characters long"
        }
        if (password != binding.registrationConfirmPassword.text.toString()) {
            binding.registrationConfirmPasswordContainer.helperText = "Passwords don't match"
        }
        if (password == binding.registrationConfirmPassword.text.toString()) {
            binding.registrationConfirmPasswordContainer.helperText = null
        }
        return null
    }

    private fun validateConfirmPassword(): String? {
        val confirmPassword = binding.registrationConfirmPassword.text.toString()
        if (confirmPassword == "") {
            return "Required"
        }
        if (confirmPassword.length < 6) {
            return "Password must be at least 6 characters long"
        }
        if (confirmPassword != binding.registrationPassword.text.toString()) {
            return "Passwords don't match"
        }
        return null
    }

    private fun validateOnConfirm(): Boolean{
        binding.registrationNameContainer.helperText = validateName()
        binding.registrationEmailContainer.helperText = validateEmail()
        binding.registrationPasswordContainer.helperText = validatePassword()
        binding.registrationConfirmPasswordContainer.helperText = validateConfirmPassword()

        if (binding.registrationNameContainer.helperText == null &&
            binding.registrationEmailContainer.helperText == null &&
            binding.registrationPasswordContainer.helperText == null &&
            binding.registrationConfirmPasswordContainer.helperText == null
        ) {
            return true
        }

        return false
    }
}
package com.example.diplomski_android.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentLoginBinding
import com.example.diplomski_android.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            loginFragment = this@LoginFragment
        }

        binding.buttonLogin.setOnClickListener {
            var user = User()
            val email = binding.tiLoginEmail.editText?.text.toString()
            val password = binding.tiLoginPassword.editText?.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val sharedPreference = activity?.getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPreference?.edit()

                val job = CoroutineScope(Dispatchers.IO).launch {
                    user = mainViewModel.getUserByEmail(email)
                }
                runBlocking { job.join() }

                editor?.remove("user")?.apply()
                editor?.putLong("user",user.id!!)
                mainViewModel.setIsAdmin(user.admin!!)
                editor?.apply()
                mainViewModel.setUser(user)

                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_coursesFragment)
            }.addOnFailureListener {
                Toast.makeText(context,"Wrong email or password",Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLoginRegister.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }
}
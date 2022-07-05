package com.riseup.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.riseup.noteapp.databinding.FragmentRegisterBinding
import com.riseup.noteapp.models.UserRequest
import com.riseup.noteapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private  val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnSignUp.setOnClickListener{
           //
            authViewModel.registerUser(UserRequest("test00@test.com","0123456789","test1111"))
        }
        binding.btnLogin.setOnClickListener {
          //  findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            authViewModel.loginUser(UserRequest("test00@test.com","0123456789","test1111"))

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.UserResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{findNavController().navigate(R.id.action_registerFragment_to_mainFragment)}
                is NetworkResult.Error ->{
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading ->{
                    binding.progressBar.isVisible = true
                }
            }
         })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
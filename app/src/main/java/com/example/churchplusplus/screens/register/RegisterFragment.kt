package com.example.churchplusplus.screens.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.R
import com.example.churchplusplus.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentRegisterBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container, false)

        binding.textView8.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.button.setOnClickListener{
            //get all the input data
            val name = binding.nomeInput.text.toString()
            val email = binding.emailInput.text.toString()
            val surname = binding.apelidoInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val churchName = binding.churchNameInput.text.toString()
            val churchLocation = binding.locationInput.text.toString()

            //organize the data to send to repo
            val user = User(name,surname,email,password)
            val church = Church(churchName,churchLocation,0.0,user)
            //send data to repo
            val res = viewModel.registerUser(user,church)
            Toast.makeText(requireContext(),res,Toast.LENGTH_LONG).show()

            //clear for on success register
            if(res == "Registado com sucesso"){
                binding.emailInput.setText("")
                binding.nomeInput.setText("")
                binding.apelidoInput.setText("")
                binding.passwordInput.setText("")
                binding.churchNameInput.setText("")
                binding.locationInput.setText("")

                //go back to login
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
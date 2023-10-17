package com.example.churchplusplus.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)

    //login function
    fun loginUser(email:String, password:String):Int{
        val user = userRepository.login(email,password)
        return user.id
    }
}
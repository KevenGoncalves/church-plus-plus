package com.example.churchplusplus.screens.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.UserRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)
    private val churchRepository = ChurchRepository(applicationContext)
    //register function
    fun registerUser(user:User, church: Church):String{
        if(user.email.trim() == "" || user.surname.trim() == ""
            || user.name.trim() == "" || user.password.trim() == ""){
            return "Por favor, preencha todos os campos"
        }else{
            //check if user exists before create
            val check = userRepository.checkUser(user)

            if(check.id != 0){
                return "Este email ja existe"
            }else{
                val res = userRepository.registerUser(user)

                if(res.toInt() != 0){
                    //register church
                    church.user.id = res.toInt()
                    val result = churchRepository.registerChurch(church)
                    if(result.toInt() != 0){
                        return "Registado com sucesso"
                    }else{
                        return "Houve um erro, volte a tentar mais tarde"
                    }
                }else{
                    return "Houve um erro, volte a tentar mais tarde"
                }
            }
        }
    }

}
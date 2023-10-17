package com.example.churchplusplus.screens.definicoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.UserRepository

class DefinicoesViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)
    private val churchRepository = ChurchRepository(applicationContext)

    fun getUserData(uid:Int):Church{
        return churchRepository.getChurchByUserId(uid)
    }

    fun updateOne(uid:Int, user: User, church: Church):String{
        val res = userRepository.updateUser(uid,user)

        if(res > 0 ){
            val rs = churchRepository.updateChurch(uid,church,'0')
            if(rs > 0){
                return "Dados atualizados com sucesso"
            }
        }

        return "Houve um erro"
    }
}
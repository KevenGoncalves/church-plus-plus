package com.example.churchplusplus.screens.contribuicoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.Contribution
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.ContributionRepository
import com.example.churchplusplus.Repository.UserRepository

class ContribuicoesViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val contributionRepository = ContributionRepository(applicationContext)

    private val churchRepository = ChurchRepository(applicationContext)

    fun getChurchByUser(userId:Int):Church{
        val res = churchRepository.getChurchByUserId(userId)

        if(res.id != 0){
            return res
        }

        return Church()
    }

    fun registerContribution(contribution: Contribution, uid:Int):String{

        if(contribution.title == "" || contribution.price <= 0 || contribution.type == ""){
            return "Dados preenchidos nao sao validos"
        }
        val res = contributionRepository.createContribution(contribution)

        if(res.toInt() > 0){
            val result = ChurchRepository(applicationContext)
                .updateChurch(uid, Church("","",contribution.price),'+')

            if(result > 0){
                return "Adicionado com sucesso"
            }

        }

        return "Houve um erro ao adicionar"
    }

    fun listContribution(uid:Int):List<Contribution>{
        return contributionRepository.getContributionByUserId(uid)
    }

    fun deleteOne(contribution: Contribution):String{
        val res = contributionRepository.deleteContribution(contribution.id)

        if(res != 0){
            val result = ChurchRepository(applicationContext)
                .updateChurch(contribution.user.id, Church("","",contribution.price),'-')
           if(result != 0){
               return "Apagado com sucesso"
           }
        }

        return "Erro ao apagar"
    }

    fun orderedList(uid:Int):List<Contribution>{
        return contributionRepository.getContributionByUserIdDesc(uid)
    }
}
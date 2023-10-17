package com.example.churchplusplus.screens.despesas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.Tuition
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.ContributionRepository
import com.example.churchplusplus.Repository.TuitionRespository

class DespesasViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val tuitionRespository = TuitionRespository(applicationContext)
    private val churchRepository = ChurchRepository(applicationContext)

    fun getUserData(uid:Int):Church{
        return churchRepository.getChurchByUserId(uid)
    }

    fun deleteOne(id:Int):Int{
        return tuitionRespository.deleteTuition(id)
    }

    fun createTuition(tuition: Tuition):String{
        if(tuition.price <= 0 || tuition.type == "" || tuition.title == "" ){
            return "Por favor preencha todos os campos"
        }

        val res = tuitionRespository.createTuition(tuition)

        if(res.toInt() > 0){
            return "Criado com sucesso"
        }

        return "Houve um erro"
    }

    fun listTuition(uid:Int):List<Tuition>{
        return tuitionRespository.getTuitionByUserId(uid)
    }

    fun countFinishedTuition(uid:Int):Int{
        return tuitionRespository.getFinishedTuition(uid)
    }

    fun countUnFinishedTuition(uid:Int):Int{
        return tuitionRespository.getUnFinishedTuition(uid)
    }

    fun updateTuition(id:Int,tuition: Tuition):String{
        val res =  tuitionRespository.updateTuition(id,tuition)

        if(res > 0 ){
            val r = churchRepository.updateChurch(tuition.user.id,Church("","",tuition.price),'-')
            if(r > 0){
                return "Atualizado com sucesso"
            }

            return "Sem saldo suficiente para marcar como concluida"
        }
        return "Houve um erro ao atualizar"
    }

    fun countAll(id:Int):Int{
        return tuitionRespository.countTuition(id)
    }

    fun getTuitionInDescOrder(uid:Int):List<Tuition>{
        return tuitionRespository.getTuitionByUserIdDesc(uid)
    }
}
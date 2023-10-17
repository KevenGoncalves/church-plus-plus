package com.example.churchplusplus.screens.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.Tuition
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.ContributionRepository
import com.example.churchplusplus.Repository.MemberRepository
import com.example.churchplusplus.Repository.TuitionRespository
import com.example.churchplusplus.Repository.UserRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val churchRepository = ChurchRepository(applicationContext)
    private val memberRepository = MemberRepository(applicationContext)
    private  val contributionRepository = ContributionRepository(applicationContext)
    private  val tuitionRepository = TuitionRespository(applicationContext)

    fun getChurchByUser(userId:Int):Church{
        val res = churchRepository.getChurchByUserId(userId)

        if(res.id != 0){
            return res
        }

        return Church()
    }

    fun getCountMembers(uid:Int):Int{
        return memberRepository.getMembersByUserId(uid).count()
    }

    fun getCountContribution(uid:Int):Int{
        return contributionRepository.getContributionByUserId(uid).count()
    }

    fun listRecentTuition(uid:Int):List<Tuition>{
        return tuitionRepository.getRecentTuitionByUserId(uid)
    }

    fun updateTuition(id:Int,tuition: Tuition):String{
        val res =  tuitionRepository.updateTuition(id,tuition)

        if(res > 0 ){
            val r = churchRepository.updateChurch(tuition.user.id,Church("","",tuition.price),'-')
            if(r > 0){
                return "Atualizado com sucesso"
            }

        }
        return "Houve um erro ao atualizar"
    }

    fun deleteOne(id:Int):Int{
        return tuitionRepository.deleteTuition(id)
    }
}
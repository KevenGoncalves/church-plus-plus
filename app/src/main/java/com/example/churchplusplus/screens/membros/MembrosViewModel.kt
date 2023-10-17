package com.example.churchplusplus.screens.membros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.Member
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.Repository.ChurchRepository
import com.example.churchplusplus.Repository.ContributionRepository
import com.example.churchplusplus.Repository.MemberRepository

class MembrosViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val memberRepository = MemberRepository(applicationContext)
    private val churchRepository = ChurchRepository(applicationContext)

    fun getUserData(uid:Int):Church{
        return churchRepository.getChurchByUserId(uid)
    }

    fun listMembers(uid:Int):List<Member>{
        return memberRepository.getMembersByUserId(uid)
    }

    fun createMember(member: Member):String{
        if(member.name == "" || member.contact == "" || member.location == ""){
            return "Por favor preencha todos os dados"
        }

        val res = memberRepository.createMember(member)

        if(res.toInt() > 0){
            return "Membro adicionado com sucesso"
        }

        return "Houve um erro, volte a tentar mais tarde"
    }

    fun deleteOne(id:Int):String{
       val res = memberRepository.deleteMember(id)

        if(res > 0){
            return "Apagado com sucesso"
        }

        return "Houve um erro o apagar"
    }

    fun getDescOrderedMembers(uid:Int):List<Member>{
        return memberRepository.getMembersByUserIdDesc(uid)
    }

}
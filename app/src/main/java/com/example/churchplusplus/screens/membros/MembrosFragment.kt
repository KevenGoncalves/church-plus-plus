package com.example.churchplusplus.screens.membros

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchplusplus.Adapter.Listeners.MemberClickListener
import com.example.churchplusplus.Adapter.MemberListAdapter
import com.example.churchplusplus.Helper.Helper
import com.example.churchplusplus.Model.Member
import com.example.churchplusplus.Model.User
import com.example.churchplusplus.R
import com.example.churchplusplus.databinding.FragmentMembrosBinding

class MembrosFragment : Fragment(),MemberClickListener {

    companion object {
        fun newInstance() = MembrosFragment()
    }

    private lateinit var viewModel: MembrosViewModel
    private lateinit var adapter:MemberListAdapter
    private lateinit var storage:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMembrosBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_membros,container,false)
        val recyclerView = binding.membroView
        viewModel = ViewModelProvider(this).get(MembrosViewModel::class.java)
        storage = requireActivity().getSharedPreferences("user",Context.MODE_PRIVATE)
        val userId = storage.getInt("id",0)
        var order = -1

        val list = viewModel.listMembers(userId)

        adapter = MemberListAdapter(list,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        //custom info
        val userData = viewModel.getUserData(userId)
        val cname = binding.root.findViewById<TextView>(R.id.custom_name)
        val profile = binding.root.findViewById<Button>(R.id.profile_pic)

        profile.setText(userData.user.name.substring(0,1))
        cname.setText(userData.user.name)
        binding.textView28.setText(adapter.itemCount.toString())

        //createNew Member
        binding.button4.setOnClickListener{
            val name = binding.churchNameInput3.text.toString()
            val contact = binding.apelidoInput3.text.toString()
            val location = binding.nomeInput3.text.toString()

            val member = Member(name,contact,location,User(id = userId))
            val res = viewModel.createMember(member)
            Toast.makeText(requireContext(),res,Toast.LENGTH_SHORT).show()
            val list = viewModel.listMembers(storage.getInt("id",0))
            adapter.updateList(list)

            //clean form
            if(res == "Membro adicionado com sucesso"){
                binding.churchNameInput3.setText("")
                binding.apelidoInput3.setText("")
                binding.nomeInput3.setText("")
            }

        }

        //change order of member list

        binding.imageButton.setOnClickListener{
            if(order == -1){
                val list = viewModel.getDescOrderedMembers(userId)
                adapter.updateList(list)
                order = 1
            }else{
                val list = viewModel.listMembers(userId)
                adapter.updateList(list)
                order = -1
            }
        }


        Helper.getGreetings(binding.root)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MembrosViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(membro: Member, view: View) {
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.membro_dropdown)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.m2 -> {
                    val res = viewModel.deleteOne(membro.id)
                    val list = viewModel.listMembers(storage.getInt("id",0))
                    adapter.updateList(list)
                    Toast.makeText(requireContext(),res,Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }

            true
        }
        popupMenu.show()
    }
}
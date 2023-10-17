package com.example.churchplusplus.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchplusplus.Adapter.Listeners.MemberClickListener
import com.example.churchplusplus.Adapter.ViewHolder.MemberViewHolder
import com.example.churchplusplus.Model.Member
import com.example.churchplusplus.R

class MemberListAdapter(var membroList:List<Member>, val membroClickListener: MemberClickListener):RecyclerView.Adapter<MemberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.membro_recycler_view,parent,false)

        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
      val membro = membroList[position]

      holder.name.setText(membro.name)
      holder.contact.setText(membro.contact)
      holder.location.setText(membro.location)
      holder.threedots.setOnClickListener {
         membroClickListener.onClick(membro,it)
      }
    }

    override fun getItemCount(): Int {
        return membroList.size
    }

    fun updateList(membroList: List<Member>){
        this.membroList = membroList
        notifyDataSetChanged()
    }
}
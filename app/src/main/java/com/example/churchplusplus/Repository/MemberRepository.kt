package com.example.churchplusplus.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.Contribution
import com.example.churchplusplus.Model.DB.Database
import com.example.churchplusplus.Model.Member
import com.example.churchplusplus.Model.User

class MemberRepository(context: Context) : SQLiteOpenHelper(context, "churchdb", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun createMember(member: Member):Long{
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("mname",member.name)
        content.put("mcontact",member.contact)
        content.put("mlocation",member.location)
        content.put("user_id",member.user.id)
        return db.insert("member",null,content)
    }

    fun getMembersByUserId(uid:Int):List<Member>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM member WHERE user_id = ?",arrayOf(uid.toString()))

        cursor.moveToFirst()
        val list = ArrayList<Member>()

        if(cursor.count > 0){
            do {
                val nIndex = cursor.getColumnIndex("mname")
                val cIndex = cursor.getColumnIndex("mcontact")
                val lIndex = cursor.getColumnIndex("mlocation")
                val userId = cursor.getColumnIndex("user_id")
                val mIdIndex = cursor.getColumnIndex("mid")

                val name = cursor.getString(nIndex)
                val contact = cursor.getString(cIndex)
                val location = cursor.getString(lIndex)
                val user = cursor.getInt(userId)
                val id = cursor.getInt(mIdIndex)

                val member = Member(name,contact,location,User(id = user),id)
                list.add(member)
            }while((cursor.moveToNext()))
        }

        return list
    }

    fun getMembersByUserIdDesc(uid:Int):List<Member>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM member WHERE user_id = ? ORDER BY mid DESC",arrayOf(uid.toString()))

        cursor.moveToFirst()
        val list = ArrayList<Member>()

        if(cursor.count > 0){
            do {
                val nIndex = cursor.getColumnIndex("mname")
                val cIndex = cursor.getColumnIndex("mcontact")
                val lIndex = cursor.getColumnIndex("mlocation")
                val userId = cursor.getColumnIndex("user_id")
                val mIdIndex = cursor.getColumnIndex("mid")

                val name = cursor.getString(nIndex)
                val contact = cursor.getString(cIndex)
                val location = cursor.getString(lIndex)
                val user = cursor.getInt(userId)
                val id = cursor.getInt(mIdIndex)

                val member = Member(name,contact,location,User(id = user),id)
                list.add(member)
            }while((cursor.moveToNext()))
        }

        return list
    }

    fun deleteMember(id:Int):Int{
        val db = this.writableDatabase
        return db.delete("member","mid = ?",arrayOf(id.toString()))
    }
}
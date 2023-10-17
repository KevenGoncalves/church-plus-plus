package com.example.churchplusplus.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.churchplusplus.Model.Church
import com.example.churchplusplus.Model.DB.Database
import com.example.churchplusplus.Model.User
import java.lang.Exception

class ChurchRepository(context:Context):SQLiteOpenHelper(context,"churchdb",null,1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun registerChurch(church: Church):Long{
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("cname",church.name)
            contentValues.put("clocation",church.location)
            contentValues.put("cmoney",church.money)
            contentValues.put("user_id",church.user.id)
            val res = db.insert("church",null,contentValues)
            return res
        }catch (e:Exception){
            return 0
        }
    }

    fun getChurchData(id:Int):Church{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM church WHERE cid = ?", arrayOf(id.toString()))
        cursor.moveToFirst()
        var church = Church()
        if(cursor.count == 1) {
            val nameInd = cursor.getColumnIndex("cname")
            val moneyIndex = cursor.getColumnIndex("cmoney")
            val locationIndex = cursor.getColumnIndex("clocation")
            val idInd = cursor.getColumnIndex("cid")

            val cname = cursor.getString(nameInd)
            val money = cursor.getDouble(moneyIndex)
            val location = cursor.getString(locationIndex)
            val churchId = cursor.getInt(idInd)
            val i = cursor.getColumnIndex("user_id")
            val uid = cursor.getInt(i)

            church = Church(cname,location,money,User(id = uid),churchId)
        }

        return church
    }

    fun getChurchByUserId(userId:Int):Church{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT user.*, church.* FROM user, church WHERE" +
                " user.id = church.user_id AND user.id = ?", arrayOf(userId.toString())
        )
        var church = Church()
        var user = User()
        cursor.moveToFirst()
        if(cursor.count == 1) {
            //user data
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val surnameIndex = cursor.getColumnIndex("surname")
            val idIndex = cursor.getColumnIndex("id")
            //church data
            val nameInd = cursor.getColumnIndex("cname")
            val moneyIndex = cursor.getColumnIndex("cmoney")
            val locationIndex = cursor.getColumnIndex("clocation")
            val idInd = cursor.getColumnIndex("cid")

            //user
            val name = cursor.getString(nameIndex)
            val email = cursor.getString(emailIndex)
            val surname = cursor.getString(surnameIndex)
            val id = cursor.getInt(idIndex)

            //church
            val cname = cursor.getString(nameInd)
            val money = cursor.getDouble(moneyIndex)
            val location = cursor.getString(locationIndex)
            val churchId = cursor.getInt(idInd)

            user = User(name,surname,email,"",id)
            church = Church(cname,location,money,user,churchId)

        }

        return church
    }

    fun updateChurch(id:Int, church: Church,sign:Char):Int{

        val res = getChurchData(id)

        if(res.id > 0) {
            val db = this.writableDatabase
            val content = ContentValues()
            if(church.name != ""){
                content.put("cname", church.name)
            }
            if(church.location != ""){
                content.put("clocation", church.location)
            }

            if(church.money > 0){
                if(sign == '-'){
                    if((res.money - church.money) >= 0) {
                        content.put("cmoney", (res.money - church.money))
                    }else{
                        content.put("cmoney", res.money)
                    }
                }else if(sign == '+'){
                    content.put("cmoney", res.money + church.money)
                }
            }
            return db.update("church",content,"cid = ?",arrayOf(id.toString()))
        }

        return 0
    }
}
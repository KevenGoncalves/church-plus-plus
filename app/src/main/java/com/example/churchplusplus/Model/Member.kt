package com.example.churchplusplus.Model

class Member(
    val name:String = "",
    val contact:String = "",
    val location:String ="",
    var user:User = User(),
    var id:Int = 0
) {
}
package com.example.churchplusplus.Model

class Church(
    var name:String = "",
    var location:String = "",
    var money:Double = 0.0,
    var user:User = User(),
    var id:Int = 0
) {
}
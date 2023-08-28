package com.example.inkentiappwhatsappclone.model

data class MessageModel(
    var message:String? = "",
    var senderId:String? = "",
    var timeStamp:Long? = 0
){
    //time stamp is not necessary
}

package com.example.openeye.logic.model

data class MessageBean(
    val messageList: ArrayList<Message>,
    val nextPageUrl: String,
    val updateTime: Long
) {
    data class Message(
        val actionUrl: String,
        val content: String,
        var date: String,
        val icon: String,
        val id: Int,
        val ifPush: Boolean,
        val pushStatus: Int,
        val title: String,
        val uid: Any,
        val viewed: Boolean
    )
}
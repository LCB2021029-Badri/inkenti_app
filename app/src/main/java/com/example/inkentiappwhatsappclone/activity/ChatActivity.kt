package com.example.inkentiappwhatsappclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.adapter.MessageAdapter
import com.example.inkentiappwhatsappclone.databinding.ActivityChatBinding
import com.example.inkentiappwhatsappclone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var senderUid:String
    private lateinit var receiverUid:String
    private lateinit var senderUidMergedReceiverUid:String
    private lateinit var receiverUidMergedSenderUid:String
    private lateinit var list:ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!
        senderUidMergedReceiverUid = senderUid+receiverUid
        receiverUidMergedSenderUid = receiverUid+senderUid
        list = ArrayList()

        binding.btnSend.setOnClickListener {
            if(binding.etMessage.text.isEmpty()){
                //do nothing
            }
            else{
                //store in DB
                val message = MessageModel(binding.etMessage.text.toString(),senderUid, Date().time)

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderUidMergedReceiverUid)
                    .child("message")
                    .child(randomKey!!)
                    .setValue(message)
                    .addOnSuccessListener {
                        //if data saved successful for sender then we must save it in receiver also
                        database.reference.child("chats")
                            .child(receiverUidMergedSenderUid)
                            .child("message")
                            .child(randomKey!!)
                            .setValue(message)
                            .addOnSuccessListener {
                                //message sent successfully
                                binding.etMessage.text = null
                            }
                    }
            }

        }

        database.reference.child("chats")
            .child(senderUidMergedReceiverUid)
            .child("message")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }

                    binding.rvChat.adapter = MessageAdapter(this@ChatActivity,list)

                }

                override fun onCancelled(error: DatabaseError) {
                    //toast message
                }

            })


    }
}
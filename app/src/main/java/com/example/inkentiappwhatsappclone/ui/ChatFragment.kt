package com.example.inkentiappwhatsappclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.databinding.FragmentChatBinding
import com.example.inkentiappwhatsappclone.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userList : ArrayList<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
        userList = ArrayList()

        database.reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for(snapshot1 in snapshot.children){
                        val user = snapshot.getValue(UserModel::class.java)
                        if(user!!.uid != FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })




        return binding.root
    }

}
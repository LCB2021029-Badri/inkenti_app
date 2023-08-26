package com.example.inkentiappwhatsappclone.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.inkentiappwhatsappclone.MainActivity
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.databinding.ActivityNumberBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNumberBinding
    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            val intent = Intent(this@NumberActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //else
        binding.btnSendOTP.setOnClickListener {
            if(binding.etPhoneNumber.text!!.isEmpty()){
                    createSnackBar(it)
            }else{
                var intent = Intent(this@NumberActivity,OTPActivity::class.java)
                intent.putExtra("number",binding.etPhoneNumber.text!!)
                startActivity(intent)
            }
        }

    }


    private fun createSnackBar(view:View){
        Snackbar.make(view,"Phone number can't be empty",Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(Color.parseColor("#006400"))
            .setAction("Edit"){
//                Toast.makeText(this,"snackbar button pressed",Toast.LENGTH_SHORT).show()
            }
            .show()
    }


}
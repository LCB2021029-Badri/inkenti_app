package com.example.inkentiappwhatsappclone.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.databinding.ActivityOtpactivityBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var verificationId:String
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        //when screen is loading
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please Wait ...")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

        //to set text in tv
        val phoneNumber = "+91"+intent.getStringExtra("number")
        binding.tvNum.text = "Enter the OTP code sent to " +phoneNumber

        //to send otp
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    createSnackBar(binding.root,"OTP verification Failed","Try Again")
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId = p0

                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.btnContinue.setOnClickListener {
            if(binding.etOtp.text!!.isEmpty()){
                createSnackBar(it,"Enter OTP","Try Again")
            }
            else{
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(verificationId,binding.etOtp.text!!.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            dialog.dismiss()
                            val intent = Intent(this@OTPActivity,ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            dialog.dismiss()
                            createSnackBar(binding.root, "Error " + it.exception,"Try Again")
                        }
                    }
            }

        }







    }


    private fun createSnackBar(view: View,text: String,actionText:String){
        Snackbar.make(view,text, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(Color.parseColor("#FF9494"))
            .setTextColor(Color.parseColor("#EE4B28"))
            .setActionTextColor(Color.parseColor("#000000"))
            .setAction(actionText){
//                Toast.makeText(this,"snackbar button pressed",Toast.LENGTH_SHORT).show()
                binding.etOtp.text = null
            }
            .show()
    }


}
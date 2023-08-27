package com.example.inkentiappwhatsappclone.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.inkentiappwhatsappclone.MainActivity
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.databinding.ActivityProfileBinding
import com.example.inkentiappwhatsappclone.model.UserModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.util.Date

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg :Uri
    private lateinit var dialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing all
        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile ...")
            .setCancelable(false)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userImage.setOnClickListener {
            getImageFormGallery()
        }

        binding.btnContinue.setOnClickListener {
            if(binding.etUserName.text!!.isEmpty()){ createSnackBar(binding.root,"user name cant be empty","try again") }
            else if(selectedImg == null){ createSnackBar(binding.root,"upload user image","try again") }
            else{
                uploadData()
            }
        }



    }

    private fun uploadData() {
        val reference = storage.reference.child("ProfileOfuser").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {task ->
                    uploadInfo(task.toString())
                }
            }
            else{
                createSnackBar(binding.root,"failed to upload image to FB storage","Try Again")
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(auth.uid.toString(),binding.etUserName.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl)
        database.reference.child("users")
            .setValue(user)
            .addOnSuccessListener {
                startActivity(Intent(this@ProfileActivity,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                createSnackBar(binding.root,"failed to upload data to Realtime DB","Try Again")
            }
    }


    private fun getImageFormGallery(){
        //implicit intent for image
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 69)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(data.data!=null){
                selectedImg = data.data!!
                binding.userImage.setImageURI(selectedImg)
            }
        }
    }


    private fun createSnackBar(view: View, text: String, actionText:String){
        Snackbar.make(view,text, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(Color.parseColor("#FF9494"))
            .setTextColor(Color.parseColor("#EE4B28"))
            .setActionTextColor(Color.parseColor("#000000"))
            .setAction(actionText){
//                Toast.makeText(this,"snackbar button pressed",Toast.LENGTH_SHORT).show()
            }
            .show()
    }









}
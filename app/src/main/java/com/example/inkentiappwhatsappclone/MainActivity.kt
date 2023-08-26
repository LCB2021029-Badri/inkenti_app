package com.example.inkentiappwhatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.inkentiappwhatsappclone.activity.NumberActivity
import com.example.inkentiappwhatsappclone.adapter.ViewPagerAdapter
import com.example.inkentiappwhatsappclone.databinding.ActivityMainBinding
import com.example.inkentiappwhatsappclone.ui.CallFragment
import com.example.inkentiappwhatsappclone.ui.ChatFragment
import com.example.inkentiappwhatsappclone.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //i f no one is logged in, go to login page
        if(auth.currentUser == null){
            val intent = Intent(this@MainActivity,NumberActivity::class.java)
            startActivity(intent)
            finish()
        }

        //view pager setup
        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())
        val adapter = ViewPagerAdapter(this@MainActivity,supportFragmentManager,fragmentArrayList)
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

    }
}
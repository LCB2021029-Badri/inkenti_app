package com.example.inkentiappwhatsappclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.inkentiappwhatsappclone.adapter.ViewPagerAdapter
import com.example.inkentiappwhatsappclone.databinding.ActivityMainBinding
import com.example.inkentiappwhatsappclone.ui.CallFragment
import com.example.inkentiappwhatsappclone.ui.ChatFragment
import com.example.inkentiappwhatsappclone.ui.StatusFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())
        val adapter = ViewPagerAdapter(this@MainActivity,supportFragmentManager,fragmentArrayList)
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

    }
}
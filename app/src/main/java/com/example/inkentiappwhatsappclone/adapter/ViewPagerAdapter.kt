package com.example.inkentiappwhatsappclone.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val context: Context,
    fm:FragmentManager?,
    val list:ArrayList<Fragment>
) : FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {

        //return list.size
        //list size is 3 in this case
        return 3;

    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //return super.getPageTitle(position)
        return TAB_TITLES[position]
    }

    companion object{
        //order is same as adapter setup in mainactivity
        val TAB_TITLES = arrayOf("Chats","Status","Call")
    }

}
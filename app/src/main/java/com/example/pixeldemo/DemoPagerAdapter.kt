package com.example.pixeldemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

data class DemoPageData(val imageRes: Int, val title: String, val desc: String)

class DemoPagerAdapter(activity: AppCompatActivity, private val pages: List<DemoPageData>)
    : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = pages.size
    override fun createFragment(position: Int): Fragment {
        val f = DemoPageFragment()
        f.arguments = Bundle().apply {
            putInt("imageRes", pages[position].imageRes)
            putString("title", pages[position].title)
            putString("desc", pages[position].desc)
        }
        return f
    }
}

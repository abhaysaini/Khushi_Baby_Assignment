package com.example.khushi_baby_assignemnt.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khushi_baby_assignemnt.databinding.ActivityMainBinding
import com.example.khushi_baby_assignemnt.ui.adapter.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = arrayOf("Popular", "Now Playing")[position]
        }.attach()
    }
}
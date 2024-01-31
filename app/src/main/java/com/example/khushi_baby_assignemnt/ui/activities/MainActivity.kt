package com.example.khushi_baby_assignemnt.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khushi_baby_assignemnt.databinding.ActivityMainBinding
import com.example.khushi_baby_assignemnt.ui.adapter.MyPagerAdapter
import com.example.khushi_baby_assignemnt.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetSnackbar()
        }
    }

    private fun setupViewPager() {
        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = arrayOf("Popular", "Now Playing")[position]
        }.attach()
    }

    private fun showNoInternetSnackbar() {
        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_LONG).show()
    }
}
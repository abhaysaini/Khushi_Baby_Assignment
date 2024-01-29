package com.example.khushi_baby_assignemnt.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.NowPlayingFragment
import com.example.khushi_baby_assignemnt.ui.fragments.popular.PopularFragment


class MyPagerAdapter  (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PopularFragment()
            1 -> return NowPlayingFragment()
        }
        return PopularFragment()
    }
}
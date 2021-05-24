package com.medialink.repoandlivesubmission.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(private val mainActivityCallback: IMainActivity, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = mainActivityCallback.getCount()

    override fun getItem(position: Int): Fragment =
        mainActivityCallback.getFragment(position)

    override fun getPageTitle(position: Int): CharSequence =
        mainActivityCallback.getPageTitle(position)
}
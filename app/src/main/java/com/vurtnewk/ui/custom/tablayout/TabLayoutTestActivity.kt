package com.vurtnewk.ui.custom.tablayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/11
 */
class TabLayoutTestActivity : AppCompatActivity() {

    companion object {

        const val LENGTH = 2

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TabLayoutTestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout_test)
        mTabLayout = findViewById(R.id.mTabLayout)
        mViewPager2 = findViewById(R.id.mViewPager2)

        mViewPager2.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {

            override fun getItemCount(): Int = LENGTH

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> TabTest01Fragment()
                    else -> TabTest02Fragment()
                }
            }
        }
        //禁止滑动
        mViewPager2.isUserInputEnabled = false

        TabLayoutMediator(mTabLayout, mViewPager2) { tab, position ->
            tab.customView = getItem(position)
        }.attach()

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                recoverItem(tab)
//                chooseTab(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun recoverItem(tab: TabLayout.Tab?) {
        repeat(mTabLayout.tabCount) {
            val tvTab = mTabLayout.getTabAt(it)?.view?.findViewById<TextView>(R.id.tv_tab)
            val indicator = mTabLayout.getTabAt(it)?.view?.findViewById<View>(R.id.view_indicator)
            if (mTabLayout.getTabAt(it) == tab) {
                tvTab?.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
                indicator?.visibility = View.VISIBLE
            } else {
                tvTab?.setTextColor(ContextCompat.getColor(this, R.color.black))
                indicator?.visibility = View.GONE
            }
        }
    }

//    private fun chooseTab(tab: TabLayout.Tab?) {
//        val tvTab = tab?.view?.findViewById<TextView>(R.id.tv_tab)
//        val indicator = tab?.view?.findViewById<View>(R.id.view_indicator)
//    }

    private fun getItem(position: Int): View {
        val inflate = layoutInflater.inflate(R.layout.layout_tab_item, null)
        val tvTab = inflate.findViewById<TextView>(R.id.tv_tab)
        val indicator = inflate.findViewById<View>(R.id.view_indicator)
        if (position == 0) {
            tvTab.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
            indicator.visibility = View.VISIBLE
        } else {
            tvTab.setTextColor(ContextCompat.getColor(this, R.color.black))
            indicator.visibility = View.GONE
        }
        return inflate
    }
}
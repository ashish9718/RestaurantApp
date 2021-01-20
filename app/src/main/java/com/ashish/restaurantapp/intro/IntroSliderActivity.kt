package com.ashish.restaurantapp.intro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.ui.main.view.activities.SigninActivity
import kotlinx.android.synthetic.main.activity_intro_slider.*

class IntroSliderActivity : AppCompatActivity() {

    private val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_intro_slider)

        val adapter = IntroSliderAdapter(this)
        vpIntroSlider.adapter = adapter

        fragmentList.addAll(listOf(
            Intro1Fragment(), Intro2Fragment(), Intro3Fragment()
        ))
        adapter.setFragmentList(fragmentList)

        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)

        registerListeners()
    }

    private fun registerListeners() {
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)

                if (position < fragmentList.lastIndex) {
                    tvSkip.visibility = View.VISIBLE
                    tvNext.text = "Next"
                } else {
                    tvSkip.visibility = View.GONE
                    tvNext.text = "Get Started"
                }
            }
        })

        tvSkip.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvNext.setOnClickListener {
            val position = vpIntroSlider.currentItem

            if (position < fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position + 1
            } else {
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}

package com.yzx.democollection.ui.bottomnavigationview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.MaterialFadeThrough
import com.yzx.democollection.R
import com.yzx.democollection.databinding.ActivityBottomNavigationViewBinding

class BottomNavigationViewActivity:AppCompatActivity() {

    lateinit var transitionFragment1: TransitionFragment
    lateinit var transitionFragment2: TransitionFragment
    lateinit var transitionFragment3: TransitionFragment
    lateinit var transitionFragment4: TransitionFragment

    lateinit var binding: ActivityBottomNavigationViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        binding = ActivityBottomNavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)

        transitionFragment1 = getFragment(R.layout.fragment_transition)
        transitionFragment2 = getFragment(R.layout.fragment_transition2)
        transitionFragment3 = getFragment(R.layout.fragment_transition3)
        transitionFragment4 = getFragment(R.layout.fragment_transition4)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, transitionFragment1)
            .add(R.id.container, transitionFragment2)
            .add(R.id.container, transitionFragment3)
            .add(R.id.container, transitionFragment4)
            .commit()
        showFragment(R.id.menu_id_home_1)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            showFragment(item.itemId)
            dealBadge(item.itemId)
            true
        }
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.menu_id_home_1)
        badge?.isVisible = true
//        badge?.number = 10
    }

    private fun dealBadge(itemId: Int) {
        binding.bottomNavigationView.getOrCreateBadge(itemId).apply {
            isVisible = false
            if (hasNumber()) {
                clearNumber()
            }
        }
    }

    private fun getFragment(layout: Int): TransitionFragment {
        val transitionFragment = TransitionFragment(layout)
        val materialFadeThrough = MaterialFadeThrough().apply {
            addTarget(R.id.text)
            addTarget(R.id.text2)
            addTarget(R.id.text3)
            addTarget(R.id.text4)
        }
        transitionFragment.enterTransition = materialFadeThrough
        return transitionFragment
    }

    private fun showFragment(itemId: Int) {
        supportFragmentManager.beginTransaction().apply {
            when (itemId) {
                R.id.menu_id_home_1 -> {
                    show(transitionFragment1)
                        .hide(transitionFragment2)
                        .hide(transitionFragment3)
                        .hide(transitionFragment4)
                }
                R.id.menu_id_home_2 -> {
                    hide(transitionFragment1)
                        .show(transitionFragment2)
                        .hide(transitionFragment3)
                        .hide(transitionFragment4)
                }
                R.id.menu_id_home_3 -> {
                    hide(transitionFragment1)
                        .hide(transitionFragment2)
                        .show(transitionFragment3)
                        .hide(transitionFragment4)
                }
                else -> {
                    hide(transitionFragment1)
                        .hide(transitionFragment2)
                        .hide(transitionFragment3)
                        .show(transitionFragment4)
                }
            }
        }.commit()
    }
}
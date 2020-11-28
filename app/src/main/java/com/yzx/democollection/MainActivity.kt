package com.yzx.democollection

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.yzx.democollection.adapter.MainAdapter
import com.yzx.democollection.databinding.ActivityMainBinding
import com.yzx.democollection.ui.bottomnavigationview.BottomNavigationViewActivity
import com.yzx.democollection.ui.search.SearchActivity
import com.yzx.lib_core.utils.AndroidVersion

class MainActivity : AppCompatActivity(), OnItemChildClickListener {

    private val titles = mutableListOf("BottomNavigationView")
    private val activities = mutableListOf(BottomNavigationViewActivity::class.java)

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val mainAdapter = MainAdapter()
        mainAdapter.apply {
            data = titles
            binding.recyclerView.adapter = this
            setOnItemChildClickListener(this@MainActivity)
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            R.id.tvTitle -> {
                startActivity(Intent(this@MainActivity, activities[position]))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            // Android 5.0版本启用transition动画会存在一些效果上的异常，因此这里只在Android 5.1以上启用此动画
            if (AndroidVersion.hasLollipopMR1()) {
                val searchMenuView: View? = binding.toolbar.findViewById(R.id.menu_search)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this, searchMenuView,
                    getString(R.string.transition_search_back)
                ).toBundle()
                startActivity(Intent(this, SearchActivity::class.java), options)
            } else {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
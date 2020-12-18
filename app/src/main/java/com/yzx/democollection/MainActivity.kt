package com.yzx.democollection

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.google.android.material.badge.BadgeDrawable
import com.yzx.democollection.adapter.MainAdapter
import com.yzx.democollection.databinding.ActivityMainBinding
import com.yzx.democollection.ui.badgedrawable.BadgeDrawableActivity
import com.yzx.democollection.ui.bottomnavigationview.BottomNavigationViewActivity
import com.yzx.democollection.ui.chip.ChipActivity
import com.yzx.democollection.ui.livedata.LiveDataActivity
import com.yzx.democollection.ui.livedata.LiveDataInstance
import com.yzx.democollection.ui.search.SearchActivity
import com.yzx.democollection.ui.trend.TrendActivity
import com.yzx.democollection.ui.trend.trend2.lottery.LottoTrendActivity
import com.yzx.lib_core.utils.AndroidVersion
import com.yzx.lib_scan.ScanActivity
import com.yzx.lib_scan.ScanActivity.Companion.SCAN_RESULT

class MainActivity : AppCompatActivity(), OnItemChildClickListener {

    companion object {
        const val REQUEST_CODE_SCAN = 1
    }

    private val titles =
        mutableListOf("BottomNavigationView", "扫码服务", "LiveData", "BadgeDrawable", "Trend", "Chip")
    private val activities =
        mutableListOf(
            BottomNavigationViewActivity::class.java,
            ScanActivity::class.java,
            LiveDataActivity::class.java,
            BadgeDrawableActivity::class.java,
            LottoTrendActivity::class.java,
            ChipActivity::class.java
        )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        LiveDataInstance.unPeekLiveData.value = "hhh"
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
                val intent = Intent(this@MainActivity, activities[position])
                if (position == 1) {
                    startActivityForResult(intent, REQUEST_CODE_SCAN)
                } else {
                    startActivity(intent)
                }
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
        } else if (item.itemId == R.id.menu_share) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "111")
            startActivity(Intent.createChooser(shareIntent, title))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            Toast.makeText(this, "扫码结果：${data?.getStringExtra(SCAN_RESULT)}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
package it.ncorti.emgvisualizer.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.R
import it.ncorti.emgvisualizer.databinding.ActivityMainBinding
import it.ncorti.emgvisualizer.ui.fragment.ControlDeviceFragment
import it.ncorti.emgvisualizer.ui.fragment.ExportFragment
import it.ncorti.emgvisualizer.ui.fragment.GraphFragment
import it.ncorti.emgvisualizer.ui.fragment.ScanDeviceFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var scanDeviceFragment: ScanDeviceFragment

    @Inject
    lateinit var controlDeviceFragment: ControlDeviceFragment

    @Inject
    lateinit var graphFragment: GraphFragment

    @Inject
    lateinit var exportFragment: ExportFragment

    private lateinit var binding: ActivityMainBinding

    @Suppress("MagicNumber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.new_toolbar))
        val fragmentList = listOf<Fragment>(scanDeviceFragment, controlDeviceFragment, graphFragment, exportFragment)
        binding.viewPager.adapter = MyAdapter(supportFragmentManager, fragmentList)
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var prevMenuItem: MenuItem? = null
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    binding.bottomNavigation.menu.getItem(0).isChecked = false
                }
                binding.bottomNavigation.menu.getItem(position).isChecked = true
                prevMenuItem =  binding.bottomNavigation.menu.getItem(position)
            }
        })
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_scan ->  binding.viewPager.currentItem = 0
                R.id.item_control ->  binding.viewPager.currentItem = 1
                R.id.item_graph ->  binding.viewPager.currentItem = 2
                R.id.item_export ->  binding.viewPager.currentItem = 3
            }
            false
        }
    }

    fun navigateToPage(pageId: Int) {
        binding.viewPager.currentItem = pageId
    }

    class MyAdapter(fm: FragmentManager, private val fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}

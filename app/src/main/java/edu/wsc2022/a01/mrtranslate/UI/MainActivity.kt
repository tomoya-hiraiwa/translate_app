package edu.wsc2022.a01.mrtranslate.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import edu.wsc2022.a01.mrtranslate.R
import edu.wsc2022.a01.mrtranslate.databinding.ActivityMainBinding
const val Token = "enter your token"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tab = binding.tab
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> changeFragment(TranslateFragment())
                    1 -> changeFragment(LibraryFragment())
                    2 -> changeFragment(SettingFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
    private fun changeFragment(fm: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,fm)
            .commit()
    }
}
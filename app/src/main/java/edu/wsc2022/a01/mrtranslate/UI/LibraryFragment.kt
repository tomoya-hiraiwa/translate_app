package edu.wsc2022.a01.mrtranslate.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import edu.wsc2022.a01.mrtranslate.DataBase.LibraryHelper
import edu.wsc2022.a01.mrtranslate.R
import edu.wsc2022.a01.mrtranslate.ViewModel.TranslateViewModel
import edu.wsc2022.a01.mrtranslate.databinding.FragmentLibraryBinding
import kotlinx.coroutines.launch


class LibraryFragment : Fragment() {
    private lateinit var viewModel: TranslateViewModel
private lateinit var binding: FragmentLibraryBinding
private lateinit var helper: LibraryHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TranslateViewModel::class.java]
        helper = LibraryHelper(requireContext())
        val list = binding.list
        list.layoutManager = LinearLayoutManager(requireContext())
        val tab = binding.tab
        viewModel.libList.observe(viewLifecycleOwner){attachData ->
            val adapter = LibraryListAdapter(attachData)
            list.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.getData(helper,1)
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
               when(tab?.position){
                   0 -> attachData(1)
                   1 -> attachData(2)
               }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
    private fun attachData(type: Int){
        lifecycleScope.launch {
            viewModel.getData(helper,type)
        }
    }


}
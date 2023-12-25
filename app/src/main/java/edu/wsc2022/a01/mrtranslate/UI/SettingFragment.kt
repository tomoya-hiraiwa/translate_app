package edu.wsc2022.a01.mrtranslate.UI

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import edu.wsc2022.a01.mrtranslate.DataBase.LibraryHelper
import edu.wsc2022.a01.mrtranslate.R
import edu.wsc2022.a01.mrtranslate.ViewModel.TranslateViewModel
import edu.wsc2022.a01.mrtranslate.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch


class SettingFragment : Fragment() {
private lateinit var binding: FragmentSettingBinding
private lateinit var helper: LibraryHelper
private lateinit var viewModel: TranslateViewModel
private lateinit var behavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = LibraryHelper(requireContext())
        behavior = BottomSheetBehavior.from(binding.sheet)
        viewModel = ViewModelProvider(requireActivity())[TranslateViewModel::class.java]
        behavior.apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.delbt.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.delcheck.setOnClickListener {
            binding.sheetDelbt.isEnabled = binding.delcheck.isChecked
        }
        binding.sheetDelbt.setOnClickListener {
        lifecycleScope.launch {
            viewModel.deleteAllData(helper)
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        }





    }



}
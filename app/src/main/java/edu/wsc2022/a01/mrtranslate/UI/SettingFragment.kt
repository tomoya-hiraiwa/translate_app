package edu.wsc2022.a01.mrtranslate.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import edu.wsc2022.a01.mrtranslate.R
import edu.wsc2022.a01.mrtranslate.ViewModel.TranslateViewModel
import edu.wsc2022.a01.mrtranslate.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
private lateinit var binding: FragmentSettingBinding
private lateinit var viewModel: TranslateViewModel
private lateinit var behavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.sheet)
        viewModel = ViewModelProvider(requireActivity())[TranslateViewModel::class.java]
        behavior.apply {
            isHideable = true

        }
    }



}
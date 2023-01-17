package com.example.objectsizemeasurement.ui.main

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.objectsizemeasurement.R
import com.example.objectsizemeasurement.databinding.ObjectSizeDecisionBottomSheetBinding

class ObjectSizeDecisionBottomSheet : DialogFragment() {
    private lateinit var binding: ObjectSizeDecisionBottomSheetBinding
    private lateinit var viewModel: MainViewModel

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val window: Window? = dialog.window
            if (window != null) {
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    companion object {
        val TAG: String = ObjectSizeDecisionBottomSheet::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.object_size_decision_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.close.setOnClickListener {
            close()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        close()
    }

    private fun close() {
        viewModel.openBottomSheet(false)
        try {


            if (!binding.heightValue.text.isNullOrEmpty() && !binding.widthValue.text.isNullOrEmpty()) {
                viewModel.updateHeight(binding.heightValue.text.toString().toDouble())
                viewModel.updateWidth(binding.widthValue.text.toString().toDouble())
                viewModel.setReferenceObject(true)

            } else {
                viewModel.updateHeight(0.0)
                viewModel.updateWidth(0.0)
                viewModel.setReferenceObject(false)
            }
        } catch ( e: Exception){
            Toast.makeText(
                requireActivity(),
                "If the entered height and width values are fractional numbers, the numbers are must be entered using  '.'",
                Toast.LENGTH_LONG
            ).show()
        }
        dismiss()
    }


}
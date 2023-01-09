package com.example.objectsizemeasurement.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objectsizemeasurement.R
import com.example.objectsizemeasurement.databinding.FragmentResultBinding
import com.example.objectsizemeasurement.ui.main.adapter.MeasurementResultAdapter
import com.example.objectsizemeasurement.ui.main.model.MeasurementResult

private lateinit var binding: FragmentResultBinding

class ResultFragment(
    private val measurementResultList : ArrayList<MeasurementResult>
) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val measurementResultAdapter = MeasurementResultAdapter(requireContext(), measurementResultList)
        binding.recListView.layoutManager = LinearLayoutManager(requireContext())
        binding.recListView.adapter = measurementResultAdapter

    }

}
package com.example.objectsizemeasurement.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.objectsizemeasurement.R
import com.example.objectsizemeasurement.databinding.FragmentMainBinding
import org.opencv.android.OpenCVLoader

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        if(OpenCVLoader.initDebug()){
            Log.d("LOADED", "success")
        }else{
            Log.d("LOADED", "error")
        }
    }

}
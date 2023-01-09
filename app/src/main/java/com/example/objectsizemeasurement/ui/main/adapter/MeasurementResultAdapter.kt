package com.example.objectsizemeasurement.ui.main.adapter

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.objectsizemeasurement.R
import com.example.objectsizemeasurement.databinding.MeasurementResultInformationBinding
import com.example.objectsizemeasurement.ui.main.model.MeasurementResult
import java.util.*
import kotlin.collections.ArrayList

class MeasurementResultAdapter(
    private val requireContext: Context,
    private val measurementResultList: ArrayList<MeasurementResult>
) : RecyclerView.Adapter<MeasurementResultAdapter.MeasurementListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementListViewHolder {
        val binding: MeasurementResultInformationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.measurement_result_information, parent, false
        )
        return MeasurementListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeasurementListViewHolder, position: Int) {
        holder.setBind(requireContext, measurementResultList[position])
    }

    override fun getItemCount(): Int {
        return measurementResultList.size
    }

    class MeasurementListViewHolder(private val binding: MeasurementResultInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setBind(
            requireContext: Context,
            measurementResult: MeasurementResult
        ) {

        }
    }
}
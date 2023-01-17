package com.example.objectsizemeasurement.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val openObjectSizeDecisionBottomSheetMLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val widthMLD: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val heightMLD: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val referenceObjectStatusMLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun openBottomSheet(value: Boolean) {
        openObjectSizeDecisionBottomSheetMLD.value = value
    }

    fun updateHeight(value: Double){
        heightMLD.value = value
    }

    fun updateWidth(value: Double){
        widthMLD.value = value
    }

    fun setReferenceObject(value: Boolean){
        referenceObjectStatusMLD.value = value
    }
}
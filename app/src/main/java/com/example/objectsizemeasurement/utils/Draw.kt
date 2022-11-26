package com.example.objectsizemeasurement.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4

class Draw(
    context: Context?, private val rect: Rect, private val text: String
) : View(context) {

    private lateinit var boundaryPaint: Paint
    private lateinit var textPaint: Paint


    init {
        init()
    }

    private fun init() {

        boundaryPaint = Paint()
        boundaryPaint.color = Color.BLUE
        boundaryPaint.strokeWidth = 10f
        boundaryPaint.style = Paint.Style.STROKE


        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 50f
        textPaint.strokeWidth = 10f
        textPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText(text, rect.centerX().toFloat(), rect.centerY().toFloat(), textPaint)
        canvas?.drawRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            boundaryPaint
        )

    }
}
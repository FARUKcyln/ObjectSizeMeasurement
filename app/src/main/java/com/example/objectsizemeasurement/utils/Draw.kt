package com.example.objectsizemeasurement.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import java.text.DecimalFormat

class Draw(
    context: Context?,
    private val rect: Rect,
    private val sizeX: Double,
    private val sizeY: Double,
    private val text: String,
    private val isReferenceObject: Boolean
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
        textPaint.color = Color.RED
        textPaint.textSize = 50f
        textPaint.strokeWidth = 50f
        textPaint.style = Paint.Style.FILL
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val dec = DecimalFormat(".##")


        canvas?.drawText(text, rect.centerX().toFloat(), rect.centerY().toFloat(), textPaint)
        canvas?.drawRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            boundaryPaint
        )

        if (sizeY > 0 && sizeX > 0) {

            if (isReferenceObject) {
                canvas?.drawText(
                    "Reference Object", rect.left.toFloat(), (rect.top - 20).toFloat(), textPaint
                )
            }

            canvas?.drawText(
                dec.format(sizeY).toString() + " cm",
                (rect.left - 80).toFloat(),
                rect.centerY().toFloat(),
                textPaint
            )

            canvas?.drawText(
                dec.format(sizeX).toString() + " cm",
                rect.centerX().toFloat(),
                (rect.bottom + 50).toFloat(),
                textPaint
            )
        }

    }
}
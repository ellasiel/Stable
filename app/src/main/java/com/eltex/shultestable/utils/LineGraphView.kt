package com.eltex.shultestable.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class LineGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var dataPoints: List<Pair<Float, Float>> = emptyList()
    private var maxX: Float = 0f
    private var maxY: Float = 0f

    init {
        paint.apply {
            color = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
            strokeWidth = 14f
            isAntiAlias = true
        }
    }

    fun setData(data: List<Pair<Float, Float>>) {
        dataPoints = data
        calculateMaxValues()
        invalidate()
    }

    private fun calculateMaxValues() {
        maxX = dataPoints.maxByOrNull { it.first }?.first ?: 0f
        maxY = dataPoints.maxByOrNull { it.second }?.second ?: 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        paint.apply {
            strokeWidth = 14f
            color = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
        }

        for (i in 0 until dataPoints.size - 1) {
            val (x1, y1) = dataPoints[i]
            val (x2, y2) = dataPoints[i + 1]
            val scaledX1 = x1 / maxX * width
            val scaledY1 = height - y1 / maxY * height
            val scaledX2 = x2 / maxX * width
            val scaledY2 = height - y2 / maxY * height
            canvas.drawLine(scaledX1, scaledY1, scaledX2, scaledY2, paint)
        }
    }
}
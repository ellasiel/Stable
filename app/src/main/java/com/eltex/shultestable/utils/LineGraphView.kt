package com.eltex.shultestable.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
    private var minX: Float = 0f
    private var minY: Float = 0f

    init {
        paint.apply {
            color = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
            strokeWidth = 14f
            isAntiAlias = true
            textSize = 40f
        }
    }

    fun setData(data: List<Pair<Float, Float>>) {
        dataPoints = data
        calculateMinMaxValues()
        invalidate()
    }

    private fun calculateMinMaxValues() {
        maxX = dataPoints.maxByOrNull { it.first }?.first ?: 0f
        maxY = dataPoints.maxByOrNull { it.second }?.second ?: 0f
        minX = dataPoints.minByOrNull { it.first }?.first ?: 0f
        minY = dataPoints.minByOrNull { it.second }?.second ?: 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val padding = 50f
        paint.color = Color.BLACK
        paint.strokeWidth = 5f

        canvas.drawLine(padding, height - padding, width, height - padding, paint)

        canvas.drawLine(padding, 0f, padding, height - padding, paint)

        paint.color = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
        paint.strokeWidth = 10f
        paint.style = Paint.Style.FILL

        for (i in 0 until dataPoints.size) {
            val (x, y) = dataPoints[i]
            val scaledX = padding + ((x - minX) / (maxX - minX)) * (width - 2 * padding)
            val scaledY = (height - padding) - ((y - minY) / (maxY - minY)) * (height - 2 * padding)
            canvas.drawCircle(scaledX, scaledY, 16f, paint)
            if (i < dataPoints.size - 1) {
                val (nextX, nextY) = dataPoints[i + 1]
                val nextScaledX = padding + ((nextX - minX) / (maxX - minX)) * (width - 2 * padding)
                val nextScaledY =
                    (height - padding) - ((nextY - minY) / (maxY - minY)) * (height - 2 * padding)
                canvas.drawLine(scaledX, scaledY, nextScaledX, nextScaledY, paint)
            }
        }
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.strokeWidth = 3f
        for (i in 0..<dataPoints.size) {
            val (x, _) = dataPoints[i]
            val scaledX = padding + ((x - minX) / (maxX - minX)) * (width - 2 * padding)
            canvas.drawText(x.toInt().toString(), scaledX, height - padding / 4, paint)
        }
        for (i in 0..<dataPoints.size) {
            val labelY = minY + (i * (maxY - minY) / dataPoints.size)
            val y = (height - padding) - (i * (height - 2 * padding) / (dataPoints.size - 1))
            canvas.drawText(String.format("%.1f", labelY), padding / 0.9f, y, paint)
        }
    }
}
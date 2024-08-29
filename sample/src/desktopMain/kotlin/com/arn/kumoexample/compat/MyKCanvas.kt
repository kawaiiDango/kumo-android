package com.arn.kumoexample.compat

import com.kennycason.kumo.compat.KumoBitmap
import com.kennycason.kumo.compat.KumoCanvas
import java.awt.Color
import java.awt.RenderingHints

class MyKCanvas(bitmap: KumoBitmap) : KumoCanvas {
    private val canvas = (bitmap as MyKBitmap).bitmap.createGraphics()
    private val fontMetrics get() = canvas.fontMetrics
    private val width = bitmap.width
    private val height = bitmap.height
    private var textSize = canvas.font.size.toFloat()

    init {
        canvas.setRenderingHints(getRenderingHints())
    }

    override fun drawBitmap(bitmap: KumoBitmap, left: Int, top: Int) {
        canvas.drawImage((bitmap as MyKBitmap).bitmap, left, top, null)
    }

    override fun drawColor(color: Int) {
        setColor(color)
        canvas.fillRect(0, 0, width, height)
    }

    override fun drawText(text: String, x: Int, y: Int) {
        canvas.drawString(text, x, y)
    }

    override fun rotate(degrees: Float, x: Float, y: Float) {
        canvas.rotate(degrees.toDouble(), x.toDouble(), y.toDouble())
    }

    override fun translate(x: Int, y: Int) {
        canvas.translate(x, y)
    }

    override fun measureText(text: String) = fontMetrics.stringWidth(text)

    override fun setColor(color: Int) {
        canvas.color = Color(color, true)
    }

    override fun setTextSize(textSize: Float) {
        if (textSize == this.textSize) return

        canvas.font = canvas.font.deriveFont(textSize)
        this.textSize = textSize
    }

    override fun getTextSize() = textSize

    override fun getFontHeight() = fontMetrics.height

    override fun getDescent() = fontMetrics.descent

    override fun getLeading() = fontMetrics.leading

    private fun getRenderingHints(): RenderingHints {
        val hints: MutableMap<RenderingHints.Key, Any?> = HashMap()
        hints[RenderingHints.KEY_ALPHA_INTERPOLATION] =
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
        hints[RenderingHints.KEY_ANTIALIASING] = RenderingHints.VALUE_ANTIALIAS_ON
        hints[RenderingHints.KEY_COLOR_RENDERING] = RenderingHints.VALUE_COLOR_RENDER_QUALITY
        hints[RenderingHints.KEY_FRACTIONALMETRICS] = RenderingHints.VALUE_FRACTIONALMETRICS_ON
        hints[RenderingHints.KEY_INTERPOLATION] = RenderingHints.VALUE_INTERPOLATION_BICUBIC
        hints[RenderingHints.KEY_TEXT_ANTIALIASING] = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB

        return RenderingHints(hints)
    }
}

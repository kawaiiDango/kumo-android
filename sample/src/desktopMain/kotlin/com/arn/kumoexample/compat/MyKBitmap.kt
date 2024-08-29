package com.arn.kumoexample.compat

import androidx.compose.ui.graphics.toComposeImageBitmap
import com.kennycason.kumo.compat.KumoBitmap
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

class MyKBitmap : KumoBitmap {
    val bitmap: BufferedImage

    constructor(width: Int, height: Int) {
        this.bitmap = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    }

    constructor(inputStream: InputStream) {
        this.bitmap = ImageIO.read(inputStream)
    }

    override fun getWidth() = bitmap.width

    override fun getHeight() = bitmap.height

    override fun getPixels(
        pixels: IntArray,
        offset: Int,
        stride: Int,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
    ) {
        bitmap.getRGB(x, y, width, height, pixels, offset, stride)
    }

    override fun recycle() {
    }

    override fun convertTo(): Any {
        return bitmap.toComposeImageBitmap()
    }
}

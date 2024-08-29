package com.arn.kumoexample.compat

import com.kennycason.kumo.compat.KumoBitmap
import com.kennycason.kumo.compat.KumoCanvas
import com.kennycason.kumo.compat.KumoGraphicsFactory
import java.io.InputStream

actual object MyKGraphicsFactory : KumoGraphicsFactory() {
    actual override fun createBitmap(width: Int, height: Int) = MyKBitmap(width, height) as KumoBitmap

    actual override fun decodeStream(inputStream: InputStream) = MyKBitmap(inputStream) as KumoBitmap

    actual override fun createCanvas(bitmap: KumoBitmap) = MyKCanvas(bitmap) as KumoCanvas
}
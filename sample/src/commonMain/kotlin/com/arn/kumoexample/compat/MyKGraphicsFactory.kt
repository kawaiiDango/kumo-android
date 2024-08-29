package com.arn.kumoexample.compat

import com.kennycason.kumo.compat.KumoBitmap
import com.kennycason.kumo.compat.KumoCanvas
import com.kennycason.kumo.compat.KumoGraphicsFactory
import java.io.InputStream

expect object MyKGraphicsFactory : KumoGraphicsFactory {
    override fun createBitmap(width: Int, height: Int): KumoBitmap

    override fun decodeStream(inputStream: InputStream): KumoBitmap

    override fun createCanvas(bitmap: KumoBitmap): KumoCanvas
}
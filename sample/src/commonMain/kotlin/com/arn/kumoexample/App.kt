package com.arn.kumoexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kennycason.kumo.CollisionMode
import com.kennycason.kumo.WordCloud
import com.kennycason.kumo.WordFrequency
import com.kennycason.kumo.bg.CircleBackground
import com.arn.kumoexample.compat.MyKGraphicsFactory
import com.kennycason.kumo.compat.KumoRect
import com.kennycason.kumo.font.scale.LinearFontScalar
import com.kennycason.kumo.image.AngleGenerator
import com.kennycason.kumo.palette.ColorPalette
import com.kennycason.kumo.palette.LinearGradientColorPalette
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val density = LocalDensity.current

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val greeting = remember { Greeting().greet() }
            val imageBitmap = remember {
                with(density) {
                    val widthPx = 300.dp.toPx().toInt() // Replace 300.dp with your desired width
                    val heightPx = 300.dp.toPx().toInt() // Replace 300.dp with your desired height
                    wordCloudTest(widthPx, heightPx)
                }
            }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(imageBitmap, null)
                Text("Compose: $greeting")
            }
        }
    }
}

fun wordCloudTest(width: Int, height: Int): ImageBitmap {

    // random word frequencies
    val wordFrequencies = listOf(
        WordFrequency("apple", 22),
        WordFrequency("baby", 3),
        WordFrequency("back", 15),
        WordFrequency("bear", 9),
        WordFrequency("boy", 26),
        WordFrequency("brown", 5),
        WordFrequency("came", 7),
        WordFrequency("cat", 19),
        WordFrequency("cloud", 11),
        WordFrequency("cow", 17),
        WordFrequency("day", 10),
        WordFrequency("dog", 13),
        WordFrequency("fox", 8),
        WordFrequency("jumped", 14),
        WordFrequency("lazy", 6),
        WordFrequency("moon", 12),
        WordFrequency("quick", 4),
        WordFrequency("red", 2),
        WordFrequency("sky", 16),
        WordFrequency("the", 1),
        WordFrequency("over", 18),
        WordFrequency("river", 20),
        WordFrequency("tree", 21),
        WordFrequency("two", 23),
        WordFrequency("white", 24),
        WordFrequency("bright", 25)
    )

    val dimension = KumoRect(width, height)
    val wordCloud = WordCloud(
        dimension,
        CollisionMode.PIXEL_PERFECT,
        MyKGraphicsFactory
    )

    val colorPalette: ColorPalette = LinearGradientColorPalette(
        Color.Red.toArgb(),
        Color.Green.toArgb(),
        10
    )

    wordCloud.setBackground(CircleBackground(dimension.width() / 2))
    wordCloud.setBackgroundColor(Color.Transparent.toArgb())
    wordCloud.setAngleGenerator(AngleGenerator(0))
    wordCloud.setPadding(4.sp.value.toInt())
    wordCloud.setFontScalar(LinearFontScalar(12.sp.value.toInt(), 48.sp.value.toInt()))
    wordCloud.setColorPalette(colorPalette)
    wordCloud.build(wordFrequencies)

    return wordCloud.bufferedImage.convertTo() as ImageBitmap
}

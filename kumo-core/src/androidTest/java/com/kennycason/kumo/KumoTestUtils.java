package com.kennycason.kumo;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.kennycason.kumo.nlp.FrequencyAnalyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class KumoTestUtils {

    public static List<WordFrequency> loadFreqFromUrl(FrequencyAnalyzer frequencyAnalyzer, final URL url) throws IOException {
        final Document doc = Jsoup.parse(url, (int) FrequencyAnalyzer.DEFAULT_URL_LOAD_TIMEOUT);
        return frequencyAnalyzer.load(Collections.singletonList(doc.body().text()));
    }

    public static String prependDataDir(String path) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String filesDir = context.getExternalFilesDir(null).getAbsolutePath() + "/";
        return filesDir + path;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[10240];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    @Test
    public void fileWriteTest() {
        File file = new File(prependDataDir(""));
        boolean canWrite = file.canWrite();
        Assert.assertTrue(canWrite);
    }
}

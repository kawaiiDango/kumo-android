package com.kennycason.kumo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KumoUtils {

   public static List<String> readLines(InputStream inputStream) throws IOException {
      return readLines(inputStream, "UTF-8");
   }

   public static List<String> readLines(InputStream inputStream, String characterEncoding) throws IOException {
      ArrayList<String> lines = new ArrayList<>();
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, characterEncoding));
      String line;
      while ((line = br.readLine()) != null) {
         lines.add(line);
      }
      return lines;
   }
}

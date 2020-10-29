package io.prismacloud.iac.commons.util;

import com.google.gson.stream.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Sacumen (www.sacumen.com)
 * This Util class will read JSON respnose.
 */
public class JSONUtils {

  public static JsonReader parseJSONWitReader(String jsonString) {
    InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
    JsonReader reader = null;
    reader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    reader.setLenient(true);
    return reader;
  }
}

package com.lokesh.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

@Slf4j
@UtilityClass
public class FileUtil {
  private static ObjectMapper mapper = new ObjectMapper();

  public static String loadJsonFile(final String path) {
    try {
      return fileToString(path);
    } catch (IOException e) {
      log.error("failed to load json file", e);
    }
    return null;
  }

  public static String fileToString(String fileClassPath) throws IOException {
    final InputStream inputStream =
        FileUtil.class.getClassLoader().getResourceAsStream(fileClassPath);
    return IOUtils.toString(inputStream);
  }
}

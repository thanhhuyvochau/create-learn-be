package org.project.createlearnbe.utils;

import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.io.IOException;

public class ImageUtil {
  public static String toBase64(MultipartFile file) {
    try {
      return Base64.getEncoder().encodeToString(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert image to Base64", e);
    }
  }
}

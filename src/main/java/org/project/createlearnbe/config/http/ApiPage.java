package org.project.createlearnbe.config.http;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ApiPage<T> {
  private List<T> data;
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;

  public ApiPage(Page<T> page) {
    this.data = page.getContent();
    this.pageNumber = page.getNumber();
    this.pageSize = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.last = page.isLast();
  }

  public static <T> ApiPage<T> of(Page<T> page) {
    return new ApiPage<>(page);
  }
}

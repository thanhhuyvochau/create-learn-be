package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.dto.request.NewsRequest;
import org.project.createlearnbe.dto.response.NewsResponse;
import org.project.createlearnbe.entities.News;
import org.project.createlearnbe.mapper.NewsMapper;
import org.project.createlearnbe.repositories.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
  private final NewsRepository newsRepository;
  private final NewsMapper newsMapper;

  public Page<NewsResponse> getAllNews(Pageable pageable) {
    return newsRepository.findAll(pageable).map(newsMapper::toResponse);
  }

  public NewsResponse getNewsById(Long id) {
    return newsRepository
        .findById(id)
        .map(newsMapper::toResponse)
        .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
  }

  public NewsResponse createNews(NewsRequest request) {
    News news = newsMapper.toEntity(request);
    return newsMapper.toResponse(newsRepository.save(news));
  }

  public NewsResponse updateNews(Long id, NewsRequest request) {
    News news =
        newsRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    newsMapper.updateEntityFromRequest(request, news);
    return newsMapper.toResponse(newsRepository.save(news));
  }

  public void deleteNews(Long id) {
    News news =
        newsRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    newsRepository.delete(news);
  }

  public Page<NewsResponse> getAllVisibleNews(Pageable pageable) {
    return newsRepository.findAllByIsDisplay(true, pageable)
        .map(newsMapper::toResponse);
  }

  public NewsResponse getVisibleNewsById(Long id) {
    Optional<News> byIsDisplayAndId = newsRepository.findByIsDisplayAndId(true, id);
    return byIsDisplayAndId
        .map(newsMapper::toResponse)
        .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
  }
}

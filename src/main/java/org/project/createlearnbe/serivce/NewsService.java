package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.dto.request.NewsRequest;
import org.project.createlearnbe.dto.response.NewsResponse;
import org.project.createlearnbe.entities.News;
import org.project.createlearnbe.mapper.NewsMapper;
import org.project.createlearnbe.repositories.NewsRepository;
import org.project.createlearnbe.utils.UrlUtils;
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
  private final UrlUtils urlUtils;

  public Page<NewsResponse> getAllNews(Pageable pageable) {
    return newsRepository
        .findAll(pageable)
        .map(
            news -> {
              NewsResponse newsResponse = newsMapper.toResponse(news);
              newsResponse.setImage(this.urlUtils.buildAbsolutePath(news.getImage()));
              return newsResponse;
            });
  }

  public NewsResponse getNewsById(Long id) {
    return newsRepository
        .findById(id)
        .map(
            news -> {
              NewsResponse response = newsMapper.toResponse(news);
              response.setImage(this.urlUtils.buildAbsolutePath(news.getImage()));
              return response;
            })
        .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
  }

  public NewsResponse createNews(NewsRequest request) {
    News news = newsMapper.toEntity(request);
    NewsResponse response = newsMapper.toResponse(newsRepository.save(news));
    response.setImage(this.urlUtils.buildAbsolutePath(news.getImage()));
    return response;
  }

  public NewsResponse updateNews(Long id, NewsRequest request) {
    News news =
        newsRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    newsMapper.updateEntityFromRequest(request, news);
    NewsResponse response = newsMapper.toResponse(newsRepository.save(news));
    response.setImage(this.urlUtils.buildAbsolutePath(news.getImage()));
    return response;
  }

  public void deleteNews(Long id) {
    News news =
        newsRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    newsRepository.delete(news);
  }

  public Page<NewsResponse> getAllVisibleNews(Pageable pageable) {
    return newsRepository
        .findAllByIsDisplay(true, pageable)
        .map(
            news -> {
              NewsResponse newsResponse = newsMapper.toResponse(news);
              newsResponse.setImage(this.urlUtils.buildAbsolutePath(news.getImage()));
              return newsResponse;
            });
  }

  public NewsResponse getVisibleNewsById(Long id) {
    Optional<News> byIsDisplayAndId = newsRepository.findByIsDisplayAndId(true, id);
    NewsResponse newsResponse =
        byIsDisplayAndId
            .map(newsMapper::toResponse)
            .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    newsResponse.setImage(this.urlUtils.buildAbsolutePath(byIsDisplayAndId.get().getImage()));
    return newsResponse;
  }
}

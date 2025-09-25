package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.NewsRequest;
import org.project.createlearnbe.dto.response.NewsResponse;
import org.project.createlearnbe.entities.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    News toEntity(NewsRequest request);

    NewsResponse toResponse(News entity);

    void updateEntityFromRequest(NewsRequest request, @MappingTarget News entity);
}

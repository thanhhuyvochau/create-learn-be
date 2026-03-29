package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.project.createlearnbe.dto.request.JobPostingRequest;
import org.project.createlearnbe.dto.response.JobPostingResponse;
import org.project.createlearnbe.entities.JobBenefit;
import org.project.createlearnbe.entities.JobPosting;
import org.project.createlearnbe.entities.JobResponsibility;

@Mapper(componentModel = "spring")
public interface JobPostingMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "responsibilities", ignore = true)
  @Mapping(target = "benefits", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  JobPosting toEntity(JobPostingRequest request);

  @Mapping(target = "badgeVariant", expression = "java(entity.getBadgeVariant() != null ? entity.getBadgeVariant().getDisplayValue() : null)")
  @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().getDisplayValue() : null)")
  JobPostingResponse toResponse(JobPosting entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "responsibilities", ignore = true)
  @Mapping(target = "benefits", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  void updateEntityFromRequest(JobPostingRequest request, @MappingTarget JobPosting entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "jobPosting", ignore = true)
  @Mapping(target = "displayOrder", ignore = true)
  JobResponsibility toResponsibilityEntity(JobPostingRequest.ResponsibilityItem item);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "jobPosting", ignore = true)
  @Mapping(target = "displayOrder", ignore = true)
  JobBenefit toBenefitEntity(JobPostingRequest.BenefitItem item);

  JobPostingResponse.ResponsibilityResponse toResponsibilityResponse(JobResponsibility entity);

  JobPostingResponse.BenefitResponse toBenefitResponse(JobBenefit entity);
}

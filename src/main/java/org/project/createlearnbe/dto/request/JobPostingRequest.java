package org.project.createlearnbe.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.project.createlearnbe.constant.BadgeVariant;
import org.project.createlearnbe.constant.JobType;

@Data
public class JobPostingRequest {

  @NotBlank
  private String title;

  @NotBlank
  private String department;

  @NotBlank
  private String location;

  private BadgeVariant badgeVariant;

  private JobType type;

  private List<String> description = new ArrayList<>();

  @Valid
  private List<ResponsibilityItem> responsibilities = new ArrayList<>();

  private List<String> requirements = new ArrayList<>();

  @Valid
  private List<BenefitItem> benefits = new ArrayList<>();

  private String deadline;

  private String recruiter;

  private String reference;

  private Boolean isActive = true;

  public record ResponsibilityItem(
      @NotBlank String icon,
      @NotBlank String title,
      String body) {}

  public record BenefitItem(
      @NotBlank String icon,
      @NotBlank String title,
      String body) {}
}

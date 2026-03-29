package org.project.createlearnbe.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class JobPostingResponse {

  private Long id;
  private String title;
  private String department;
  private String location;

  /** Lowercase display value — "primary" / "secondary" / "tertiary" */
  private String badgeVariant;

  /** Human-readable display value — "Full-time" / "Contract" / "Part-time" */
  private String type;

  private List<String> description;
  private List<ResponsibilityResponse> responsibilities;
  private List<String> requirements;
  private List<BenefitResponse> benefits;

  private String deadline;
  private String recruiter;
  private String reference;
  private Boolean isActive;

  @Data
  public static class ResponsibilityResponse {
    private String icon;
    private String title;
    private String body;
  }

  @Data
  public static class BenefitResponse {
    private String icon;
    private String title;
    private String body;
  }
}

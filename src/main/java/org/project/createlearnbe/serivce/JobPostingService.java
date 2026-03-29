package org.project.createlearnbe.serivce;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.exception.types.ResourceNotFoundException;
import org.project.createlearnbe.dto.request.JobPostingRequest;
import org.project.createlearnbe.dto.response.JobPostingResponse;
import org.project.createlearnbe.entities.JobBenefit;
import org.project.createlearnbe.entities.JobPosting;
import org.project.createlearnbe.entities.JobResponsibility;
import org.project.createlearnbe.mapper.JobPostingMapper;
import org.project.createlearnbe.repositories.JobPostingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostingService {

  private final JobPostingRepository jobPostingRepository;
  private final JobPostingMapper jobPostingMapper;

  /** Admin — all non-deleted postings, paginated. */
  public Page<JobPostingResponse> getAllAdmin(Pageable pageable) {
    return jobPostingRepository.findAllByIsDeletedFalse(pageable)
        .map(jobPostingMapper::toResponse);
  }

  /** Public — active postings only, with optional department/location filter. */
  public Page<JobPostingResponse> getPublic(String department, String location, Pageable pageable) {
    String dept = (department != null && department.isBlank()) ? null : department;
    String loc = (location != null && location.isBlank()) ? null : location;
    return jobPostingRepository.findPublicJobPostings(dept, loc, pageable)
        .map(jobPostingMapper::toResponse);
  }

  /** Fetch a single posting by id — available to both public and admin. */
  public JobPostingResponse getById(Long id) {
    JobPosting posting = jobPostingRepository.findById(id)
        .filter(j -> !j.getIsDeleted())
        .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with id: " + id));
    return jobPostingMapper.toResponse(posting);
  }

  /** Create a new job posting. */
  @Transactional
  public JobPostingResponse create(JobPostingRequest request) {
    JobPosting posting = jobPostingMapper.toEntity(request);
    applyChildCollections(posting, request);
    return jobPostingMapper.toResponse(jobPostingRepository.save(posting));
  }

  /** Update an existing job posting. */
  @Transactional
  public JobPostingResponse update(Long id, JobPostingRequest request) {
    JobPosting posting = jobPostingRepository.findById(id)
        .filter(j -> !j.getIsDeleted())
        .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with id: " + id));

    jobPostingMapper.updateEntityFromRequest(request, posting);

    // Clear and rebuild child collections so orphanRemoval handles deletes
    posting.getResponsibilities().clear();
    posting.getBenefits().clear();
    applyChildCollections(posting, request);

    return jobPostingMapper.toResponse(jobPostingRepository.save(posting));
  }

  /** Soft-delete a job posting. */
  @Transactional
  public void delete(Long id) {
    JobPosting posting = jobPostingRepository.findById(id)
        .filter(j -> !j.getIsDeleted())
        .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with id: " + id));
    posting.setIsDeleted(true);
    posting.setDeletedAt(Instant.now());
    jobPostingRepository.save(posting);
  }

  // ── helpers ──────────────────────────────────────────────────────────────

  private void applyChildCollections(JobPosting posting, JobPostingRequest request) {
    if (request.getResponsibilities() != null) {
      List<JobResponsibility> responsibilities = new ArrayList<>();
      for (int i = 0; i < request.getResponsibilities().size(); i++) {
        JobResponsibility r = jobPostingMapper.toResponsibilityEntity(
            request.getResponsibilities().get(i));
        r.setJobPosting(posting);
        r.setDisplayOrder(i);
        responsibilities.add(r);
      }
      posting.getResponsibilities().addAll(responsibilities);
    }

    if (request.getBenefits() != null) {
      List<JobBenefit> benefits = new ArrayList<>();
      for (int i = 0; i < request.getBenefits().size(); i++) {
        JobBenefit b = jobPostingMapper.toBenefitEntity(request.getBenefits().get(i));
        b.setJobPosting(posting);
        b.setDisplayOrder(i);
        benefits.add(b);
      }
      posting.getBenefits().addAll(benefits);
    }
  }
}

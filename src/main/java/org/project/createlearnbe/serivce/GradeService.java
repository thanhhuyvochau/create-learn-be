package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.dto.request.GradeRequest;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.entities.Grade;
import org.project.createlearnbe.mapper.GradeMapper;
import org.project.createlearnbe.repositories.GradeRepository;
import org.project.createlearnbe.utils.ImageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

  private final GradeRepository gradeRepository;
  private final GradeMapper gradeMapper;

  public ApiPage<GradeResponse> getAll(Pageable pageable) {
    Page<Grade> grades = gradeRepository.findAll(pageable);

    return ApiPage.of(grades.map(gradeMapper::toResponse));
  }

  public GradeResponse getById(Long id) {
    Grade grade =
        gradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found"));
    return gradeMapper.toResponse(grade);
  }

  public GradeResponse create(GradeRequest request) {
    Grade grade = new Grade();
    grade.setName(request.getName());
    grade.setDescription(request.getDescription());

    if (request.getIcon() != null && !request.getIcon().isEmpty()) {
      grade.setIconBase64(ImageUtil.toBase64(request.getIcon()));
    }

    return gradeMapper.toResponse(gradeRepository.save(grade));
  }

  public GradeResponse update(Long id, GradeRequest request) {
    Grade grade =
        gradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found"));

    grade.setName(request.getName());
    grade.setDescription(request.getDescription());

    if (request.getIcon() != null && !request.getIcon().isEmpty()) {
      grade.setIconBase64(ImageUtil.toBase64(request.getIcon()));
    }

    return gradeMapper.toResponse(gradeRepository.save(grade));
  }

  public void delete(Long id) {
    gradeRepository.deleteById(id);
  }
}

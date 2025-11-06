package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.dto.request.SubjectRequest;
import org.project.createlearnbe.dto.response.SubjectResponse;
import org.project.createlearnbe.entities.Subject;
import org.project.createlearnbe.mapper.SubjectMapper;
import org.project.createlearnbe.repositories.SubjectRepository;
import org.project.createlearnbe.utils.ImageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;

  public ApiPage<SubjectResponse> getAll(Pageable pageable) {
    Page<Subject> subjects = subjectRepository.findAll(pageable);
    return ApiPage.of(subjects.map(subjectMapper::toResponse));
  }

  public SubjectResponse create(SubjectRequest request) {
    Subject subject = new Subject();
    subject.setName(request.getName());
    subject.setDescription(request.getDescription());
    if (request.getIcon() != null && !request.getIcon().isEmpty()) {
      subject.setIconBase64(ImageUtil.toBase64(request.getIcon()));
    }
    return subjectMapper.toResponse(subjectRepository.save(subject));
  }

  public SubjectResponse update(Long id, SubjectRequest request) {
    Subject subject =
        subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
    subject.setName(request.getName());
    subject.setDescription(request.getDescription());
    if (request.getIcon() != null && !request.getIcon().isEmpty()) {
      subject.setIconBase64(ImageUtil.toBase64(request.getIcon()));
    }
    return subjectMapper.toResponse(subjectRepository.save(subject));
  }

  public void delete(Long id) {
    subjectRepository.deleteById(id);
  }
}

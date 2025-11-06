package org.project.createlearnbe.serivce;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.dto.request.ClassRequest;
import org.project.createlearnbe.dto.request.GetClassRequest;
import org.project.createlearnbe.dto.response.*;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.entities.Grade;
import org.project.createlearnbe.entities.Subject;
import org.project.createlearnbe.entities.Teacher;
import org.project.createlearnbe.mapper.ScheduleMapper;
import org.project.createlearnbe.repositories.ClazzRepository;
import org.project.createlearnbe.repositories.GradeRepository;
import org.project.createlearnbe.repositories.SubjectRepository;
import org.project.createlearnbe.repositories.TeacherRepository;
import org.project.createlearnbe.utils.UrlUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClazzService {

  private final ClazzRepository clazzRepository;
  private final SubjectRepository subjectRepository;
  private final GradeRepository gradeRepository;
  private final TeacherRepository teacherRepository;
  private final UrlUtils urlUtils;
  private final ScheduleMapper scheduleMapper;

  public ApiPage<ClassResponse> getAll(Pageable pageable) {
    Page<Clazz> classes = clazzRepository.findAll(pageable);
    return ApiPage.of(classes.map(this::toResponse));
  }

  public ClassResponse getById(Long id) {
    Clazz clazz =
        clazzRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    return toResponse(clazz);
  }

  public ClassResponse create(ClassRequest request) {
    Clazz clazz = new Clazz();
    mapRequestToEntity(request, clazz);
    return toResponse(clazzRepository.save(clazz));
  }

  public ClassResponse update(Long id, ClassRequest request) {
    Clazz clazz =
        clazzRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    mapRequestToEntity(request, clazz);
    return toResponse(clazzRepository.save(clazz));
  }

  public void delete(Long id) {
    if (!clazzRepository.existsById(id)) {
      throw new EntityNotFoundException("Class not found with id: " + id);
    }
    clazzRepository.deleteById(id);
  }

  private void mapRequestToEntity(ClassRequest request, Clazz clazz) {
    clazz.setName(request.getName());
    clazz.setBrief(request.getBrief());
    clazz.setDescription(request.getDescription());
    clazz.setImage(request.getImage());
    clazz.setRequirement(request.getRequirement());
    clazz.setGuarantee(request.getGuarantee());
    clazz.setIsDisplayed(request.getIsDisplayed());
    clazz.setPrice(request.getPrice() == null ? BigDecimal.ZERO : request.getPrice());
    if (request.getSubjectIds() != null) {
      List<Subject> subjects = subjectRepository.findAllById(request.getSubjectIds());
      clazz.setSubjects(subjects);
    }

    if (request.getGradeIds() != null) {
      List<Grade> grades = gradeRepository.findAllById(request.getGradeIds());
      clazz.setGrades(grades);
    }

    if (request.getTeacherId() != null) {
      Teacher teacher =
          teacherRepository
              .findById(request.getTeacherId())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Teacher not found with id: " + request.getTeacherId()));
      clazz.setTeacher(teacher);
    }
  }

  private ClassResponse toResponse(Clazz clazz) {
    ClassResponse response = new ClassResponse();
    response.setId(clazz.getId());
    response.setName(clazz.getName());
    response.setBrief(clazz.getBrief());
    response.setDescription(clazz.getDescription());
    response.setImage(urlUtils.buildAbsolutePath(clazz.getImage()));
    response.setRequirement(clazz.getRequirement());
    response.setGuarantee(clazz.getGuarantee());
    response.setIsDisplayed(clazz.getIsDisplayed());
    response.setPrice(clazz.getPrice());
    response.setSubjects(
        clazz.getSubjects().stream()
            .map(
                s -> {
                  var sr = new SubjectResponse();
                  sr.setId(s.getId());
                  sr.setName(s.getName());
                  sr.setDescription(s.getDescription());
                  sr.setIconBase64(s.getIconBase64());
                  return sr;
                })
            .toList());

    response.setGrades(
        clazz.getGrades().stream()
            .map(
                g -> {
                  var gr = new GradeResponse();
                  gr.setId(g.getId());
                  gr.setName(g.getName());
                  gr.setDescription(g.getDescription());
                  gr.setIconBase64(g.getIconBase64());
                  return gr;
                })
            .toList());

    if (clazz.getTeacher() != null) {
      Teacher teacher = clazz.getTeacher();
      var tr = new TeacherResponseDto();
      tr.setId(teacher.getId());
      tr.setFirstName(teacher.getFirstName());
      tr.setLastName(teacher.getLastName());
      tr.setIntroduction(teacher.getIntroduction());
      tr.setGender(teacher.getGender());
      tr.setProfileImageUrl(teacher.getProfileImageUrl());
      response.setTeacher(tr);
    }

    if (clazz.getSchedules() != null) {
      response.setScheduleResponses(
          clazz.getSchedules().stream().map(scheduleMapper::toResponse).toList());
    }

    return response;
  }

  public ApiPage<ClassResponse> getAllPublicClass(GetClassRequest request) {
    String type = request.getType() == null ? "" : request.getType().toLowerCase();

    boolean isFree = type.equals("free");
    boolean isPopular = type.equals("popular");

    Page<Clazz> classes =
        clazzRepository.findPublicClasses(
            isFree, isPopular, request.getGradeId(), request.getSubjectId(), request.getPageable());

    return ApiPage.of(classes.map(this::toResponse));
  }

  public ClassResponse getPublicClassById(Long id) {
    Clazz clazz =
        clazzRepository
            .findClazzByIsDisplayedAndId(true, id)
            .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    clazz.setClickCount(clazz.getClickCount() + 1);
    clazzRepository.save(clazz);
    return toResponse(clazz);
  }

  private boolean isMatchGradeAndSubject(Long gradeId, Long subjectId, Clazz clazz) {
    boolean result = true;
    if (gradeId != null && gradeId > 0) {
      result = clazz.getGrades().stream().anyMatch(g -> g.getId().equals(gradeId));
    }
    if (subjectId != null && subjectId > 0) {
      result = clazz.getSubjects().stream().anyMatch(s -> s.getId().equals(subjectId));
    }
    return result;
  }
}

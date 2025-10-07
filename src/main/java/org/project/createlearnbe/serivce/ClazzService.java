package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.minio.MinioProperties;
import org.project.createlearnbe.dto.request.ClassRequest;
import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.dto.response.SubjectResponse;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.entities.Subject;
import org.project.createlearnbe.entities.Grade;
import org.project.createlearnbe.entities.Teacher;
import org.project.createlearnbe.repositories.ClazzRepository;
import org.project.createlearnbe.repositories.SubjectRepository;
import org.project.createlearnbe.repositories.GradeRepository;
import org.project.createlearnbe.repositories.TeacherRepository;
import org.project.createlearnbe.utils.UrlUtils;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClazzService {

  private final ClazzRepository clazzRepository;
  private final SubjectRepository subjectRepository;
  private final GradeRepository gradeRepository;
  private final TeacherRepository teacherRepository;
  private final UrlUtils urlUtils;

  public List<ClassResponse> getAll() {
    return clazzRepository.findAll().stream().map(this::toResponse).toList();
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

    return response;
  }

  public List<ClassResponse> getAllPublicClass() {
    return clazzRepository.findAllByIsDisplayed(true).stream().map(this::toResponse).toList();
  }

  public ClassResponse getPublicClassById(Long id) {
    Clazz clazz =
        clazzRepository
            .findClazzByIsDisplayedAndId(true, id)
            .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    return toResponse(clazz);
  }
}

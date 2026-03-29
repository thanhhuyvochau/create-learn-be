package org.project.createlearnbe.serivce;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.createlearnbe.dto.request.ClassRequest;
import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.mapper.ScheduleMapper;
import org.project.createlearnbe.repositories.ClazzRepository;
import org.project.createlearnbe.repositories.GradeRepository;
import org.project.createlearnbe.repositories.SubjectRepository;
import org.project.createlearnbe.repositories.TeacherRepository;
import org.project.createlearnbe.utils.UrlUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClazzService - null teacher handling")
class ClazzServiceNullTeacherTest {

  @Mock private ClazzRepository clazzRepository;
  @Mock private SubjectRepository subjectRepository;
  @Mock private GradeRepository gradeRepository;
  @Mock private TeacherRepository teacherRepository;
  @Mock private UrlUtils urlUtils;
  @Mock private ScheduleMapper scheduleMapper;
  @Mock private RegistrationService registrationService;

  @InjectMocks private ClazzService clazzService;

  private ClassRequest requestWithoutTeacher;

  @BeforeEach
  void setUp() {
    requestWithoutTeacher = new ClassRequest();
    requestWithoutTeacher.setName("Unassigned Class");
    requestWithoutTeacher.setTeacherId(null);
    requestWithoutTeacher.setPrice(BigDecimal.ZERO);
    requestWithoutTeacher.setSubjectIds(List.of());
    requestWithoutTeacher.setGradeIds(List.of());
  }

  @Test
  @DisplayName("create() - should persist class without teacher when teacherId is null")
  void createClass_withNullTeacherId_shouldSaveWithoutTeacher() {
    Clazz savedClazz = new Clazz();
    savedClazz.setName("Unassigned Class");
    savedClazz.setTeacher(null);
    savedClazz.setSubjects(List.of());
    savedClazz.setGrades(List.of());

    when(clazzRepository.save(any(Clazz.class))).thenReturn(savedClazz);
    when(urlUtils.stripMinioExternalUrl(any())).thenReturn(null);
    when(urlUtils.buildAbsolutePath(any())).thenReturn(null);

    ClassResponse response = clazzService.create(requestWithoutTeacher);

    assertNotNull(response, "Response should not be null");
    assertNull(response.getTeacher(), "Teacher should be null in response");
    verify(teacherRepository, never()).findById(any());
    verify(clazzRepository, times(1)).save(any(Clazz.class));
  }

  @Test
  @DisplayName("update() - should persist class without teacher when teacherId is null")
  void updateClass_withNullTeacherId_shouldSaveWithoutTeacher() {
    Clazz existingClazz = new Clazz();
    existingClazz.setName("Old Name");
    existingClazz.setTeacher(null);
    existingClazz.setSubjects(List.of());
    existingClazz.setGrades(List.of());
    existingClazz.setIsDeleted(false);

    Clazz savedClazz = new Clazz();
    savedClazz.setName("Unassigned Class");
    savedClazz.setTeacher(null);
    savedClazz.setSubjects(List.of());
    savedClazz.setGrades(List.of());

    when(clazzRepository.findById(1L)).thenReturn(Optional.of(existingClazz));
    when(clazzRepository.save(any(Clazz.class))).thenReturn(savedClazz);
    when(urlUtils.stripMinioExternalUrl(any())).thenReturn(null);
    when(urlUtils.buildAbsolutePath(any())).thenReturn(null);

    ClassResponse response = clazzService.update(1L, requestWithoutTeacher);

    assertNotNull(response, "Response should not be null");
    assertNull(response.getTeacher(), "Teacher should be null in response");
    verify(teacherRepository, never()).findById(any());
    verify(clazzRepository, times(1)).save(any(Clazz.class));
  }
}

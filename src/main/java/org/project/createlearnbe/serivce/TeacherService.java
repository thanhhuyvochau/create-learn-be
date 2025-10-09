package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.dto.request.TeacherRequestDto;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.entities.Teacher;
import org.project.createlearnbe.mapper.TeacherMapper;
import org.project.createlearnbe.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.project.createlearnbe.config.http.ApiPage;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherResponseDto createTeacher(TeacherRequestDto dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherResponseDto getTeacherById(long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
        return teacherMapper.toDto(teacher);
    }

    public ApiPage<TeacherResponseDto> getAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);

        return ApiPage.of(teachers.map(teacherMapper::toDto));
    }

    public TeacherResponseDto updateTeacher(long id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        teacherMapper.updateEntityFromDto(dto, teacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public void deleteTeacher(long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id " + id);
        }
        teacherRepository.deleteById(id);
    }
}
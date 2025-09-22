package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;

import org.project.createlearnbe.config.exception.types.ResourceNotFoundException;
import org.project.createlearnbe.dto.request.GradeRequest;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.entities.Grade;
import org.project.createlearnbe.mapper.GradeMapper;
import org.project.createlearnbe.repositories.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    @Transactional
    public GradeResponse create(GradeRequest request) {
        if (gradeRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Grade with name already exists: " + request.getName());
        }
        Grade grade = gradeMapper.toEntity(request);
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    @Transactional(readOnly = true)
    public List<GradeResponse> getAll() {
        return gradeRepository.findAll()
                .stream()
                .map(gradeMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public GradeResponse getById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
        return gradeMapper.toResponse(grade);
    }

    @Transactional
    public GradeResponse update(Long id, GradeRequest request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));

        gradeMapper.updateEntityFromRequest(request, grade);
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    @Transactional
    public void delete(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grade not found with id: " + id);
        }
        gradeRepository.deleteById(id);
    }
}

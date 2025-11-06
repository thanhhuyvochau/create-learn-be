package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.constant.ProcessStatus;
import org.project.createlearnbe.dto.request.ConsultationRequest;
import org.project.createlearnbe.dto.response.ConsultationResponse;
import org.project.createlearnbe.entities.Consultation;
import org.project.createlearnbe.mapper.ConsultationMapper;
import org.project.createlearnbe.repositories.ConsultationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultationService {

  private final ConsultationRepository consultationRepository;
  private final ConsultationMapper mapper;

  @Transactional
  public ConsultationResponse create(ConsultationRequest request) {
    Consultation entity = mapper.toEntity(request);
    entity.setStatus(ProcessStatus.PROCESSING);
    return mapper.toResponse(consultationRepository.save(entity));
  }

  @Transactional(readOnly = true)
  public ApiPage<ConsultationResponse> getAll(Pageable pageable) {
    return ApiPage.of(
        consultationRepository.findAllSortedByStatusAndCreatedAt(pageable).map(mapper::toResponse));
  }

  @Transactional(readOnly = true)
  public ConsultationResponse getById(Long id) {
    Consultation consultation =
        consultationRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Consultation not found with id " + id));
    return mapper.toResponse(consultation);
  }

  @Transactional
  public ConsultationResponse update(Long id) {
    Consultation consultation =
        consultationRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Consultation not found with id " + id));
    consultation.setStatus(ProcessStatus.PROCESSED);
    consultation = consultationRepository.save(consultation);
    return mapper.toResponse(consultationRepository.save(consultation));
  }

  @Transactional
  public void delete(Long id) {
    if (!consultationRepository.existsById(id)) {
      throw new IllegalArgumentException("Consultation not found with id " + id);
    }
    consultationRepository.deleteById(id);
  }
}

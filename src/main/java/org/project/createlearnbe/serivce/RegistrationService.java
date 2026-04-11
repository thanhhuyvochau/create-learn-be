package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.constant.ProcessStatus;
import org.project.createlearnbe.dto.request.ChangeStatusRegistrationRequest;
import org.project.createlearnbe.dto.request.RegistrationRequest;
import org.project.createlearnbe.dto.response.RegistrationResponse;
import org.project.createlearnbe.entities.Clazz;
import org.project.createlearnbe.entities.Registration;
import org.project.createlearnbe.mapper.ClassMapper;
import org.project.createlearnbe.repositories.ClazzRepository;
import org.project.createlearnbe.repositories.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

  private final RegistrationRepository registrationRepository;
  private final ClazzRepository clazzRepository;
  private final ClassMapper classMapper;

  private RegistrationResponse toResponse(Registration registration) {
    RegistrationResponse response = new RegistrationResponse();
    response.setId(registration.getId());
    response.setCustomerName(registration.getCustomerName());
    response.setCustomerEmail(registration.getCustomerEmail());
    response.setPhoneNumber(registration.getPhoneNumber());
    response.setStatus(registration.getStatus());
    response.setCreatedAt(registration.getCreatedAt());
    response.setUpdatedAt(registration.getUpdatedAt());
    response.setCreatedBy(registration.getCreatedBy());
    response.setUpdatedBy(registration.getUpdatedBy());
    response.setClassResponse(classMapper.toResponse(registration.getClazz()));
    return response;
  }

  private void mapRequestToEntity(
      RegistrationRequest request, Registration registration, String action) {
    registration.setCustomerName(request.getCustomerName());
    registration.setCustomerEmail(request.getCustomerEmail());
    registration.setPhoneNumber(request.getPhoneNumber());
    Clazz clazz =
        clazzRepository
            .findById(request.getClazzId())
            .orElseThrow(
                () -> new RuntimeException("Class not found with id " + request.getClazzId()));
    registration.setClazz(clazz);
    if (action.equals("create")) {
      registration.setStatus(ProcessStatus.PROCESSING);
    } else if (action.equals("update")) {
      if (request.getStatus() != null) {
        registration.setStatus(request.getStatus());
      }
    }
  }

  public RegistrationResponse create(RegistrationRequest request) {
    Registration registration = new Registration();
    mapRequestToEntity(request, registration, "create");
    registrationRepository.save(registration);
    return toResponse(registration);
  }

  public RegistrationResponse update(Long id, ChangeStatusRegistrationRequest request) {
    Registration registration =
        registrationRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
    registration.setStatus(request.getStatus());
    registrationRepository.save(registration);
    return toResponse(registration);
  }

  public void delete(Long id) {
    if (!registrationRepository.existsById(id)) {
      throw new RuntimeException("Registration not found with id " + id);
    }
    registrationRepository.deleteById(id);
  }

  public void markRegistrationsAsClassDeleted(Long clazzId) {
    registrationRepository.findAll().stream()
        .filter(r -> clazzId.equals(r.getClazz().getId()))
        .forEach(
            r -> {
              r.setStatus(ProcessStatus.CLASS_DELETED);
              registrationRepository.save(r);
            });
  }

  public RegistrationResponse getById(Long id) {
    Registration registration =
        registrationRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
    if (registration.getStatus() == ProcessStatus.CLASS_DELETED) {
      throw new RuntimeException("Registration not found with id " + id);
    }
    return toResponse(registration);
  }

  public Page<RegistrationResponse> getAll(String search, Pageable pageable) {
    return (search != null && !search.isBlank()
            ? registrationRepository.findBySearch(search, pageable)
            : registrationRepository.findAllSortedByStatusAndCreatedAt(pageable))
        .map(this::toResponse);
  }
}

package org.project.createlearnbe.serivce;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.constant.ProcessStatus;
import org.project.createlearnbe.dto.request.ChangeStatusRegistrationRequest;
import org.project.createlearnbe.dto.request.RegistrationRequest;
import org.project.createlearnbe.dto.response.RegistrationResponse;
import org.project.createlearnbe.entities.Registration;
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

  private RegistrationResponse toResponse(Registration registration) {
    RegistrationResponse response = new RegistrationResponse();
    response.setId(registration.getId());
    response.setCustomerName(registration.getCustomerName());
    response.setCustomerEmail(registration.getCustomerEmail());
    response.setPhoneNumber(registration.getPhoneNumber());
    response.setStatus(registration.getStatus());
    return response;
  }

  private void mapRequestToEntity(
      RegistrationRequest request, Registration registration, String action) {
    registration.setCustomerName(request.getCustomerName());
    registration.setCustomerEmail(request.getCustomerEmail());
    registration.setPhoneNumber(request.getPhoneNumber());
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

  public RegistrationResponse getById(Long id) {
    return registrationRepository
        .findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
  }

  public Page<RegistrationResponse> getAll(Pageable pageable) {
    return registrationRepository.findAll(pageable).map(this::toResponse);
  }
}

package org.project.createlearnbe.mapper;

import org.mapstruct.Mapper;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  AccountResponse toResponse(Account account);
}

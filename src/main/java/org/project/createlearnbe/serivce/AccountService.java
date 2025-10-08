package org.project.createlearnbe.serivce;

import org.project.createlearnbe.constant.Role;
import org.project.createlearnbe.dto.request.ChangePasswordByAdminRequest;
import org.project.createlearnbe.dto.request.ChangePasswordByOwnerRequest;
import org.project.createlearnbe.dto.request.DeactivateAccountRequest;
import org.project.createlearnbe.dto.request.RegisterRequest;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.mapper.AccountMapper;
import org.project.createlearnbe.repositories.AccountRepository;
import org.project.createlearnbe.utils.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountMapper accountMapper;

  public AccountService(
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder,
      AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountMapper = accountMapper;
  }

  private boolean existsByUsername(String username) {
    return accountRepository.existsByUsername(username);
  }

  private boolean existsByEmail(String email) {
    return accountRepository.existsByEmail(email);
  }

  private boolean existsByPhone(String phone) {
    return accountRepository.existsByPhone(phone);
  }

  public AccountResponse register(RegisterRequest request) {
    Account account = new Account();
    if (existsByUsername(request.getUsername())) {
      throw new RuntimeException("Username already exists");
    } else if (existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already exists");
    } else if (existsByPhone(request.getPhone())) {
      throw new RuntimeException("Phone already exists");
    } else {
      account.setUsername(request.getUsername());
      account.setEmail(request.getEmail());
      account.setPhone(request.getPhone());
      account.setPassword(passwordEncoder.encode(request.getPassword()));
      account.setRole(Role.ADMIN);
      return accountMapper.toResponse(accountRepository.save(account));
    }
  }

  public AccountResponse getProfile() {
    Authentication currentAuthentication = AuthUtil.getCurrentAuthentication();
    User user = (User) currentAuthentication.getPrincipal();
    Account loginAccount =
        accountRepository
            .findByUsername(user.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
    return accountMapper.toResponse(loginAccount);
  }

  public String changePasswordByAdmin(UUID accountId, ChangePasswordByAdminRequest request) {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    account.setPassword(passwordEncoder.encode(request.getNewPassword()));
    accountRepository.save(account);
    return "Password changed successfully by admin";
  }

  public String changePasswordByOwner(UUID accountId, ChangePasswordByOwnerRequest request) {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
      throw new RuntimeException("Old password is incorrect");
    }
    account.setPassword(passwordEncoder.encode(request.getNewPassword()));
    accountRepository.save(account);
    return "Password changed successfully";
  }

  public String deactivateAccount(UUID accountId, DeactivateAccountRequest request) {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    account.setActivated(request.getActivated());
    accountRepository.save(account);
    return request.getActivated() ? "Account activated" : "Account deactivated";
  }
}

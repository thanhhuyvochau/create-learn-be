package org.project.createlearnbe.serivce;

import org.project.createlearnbe.constant.Role;
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
}

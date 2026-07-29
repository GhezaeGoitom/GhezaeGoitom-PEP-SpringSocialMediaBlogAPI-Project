package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidRegistrationException;
import com.example.exception.UnauthorizedUserException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

@Autowired  
private AccountRepository accountRepository;

public AccountService(AccountRepository accountRepository){
this.accountRepository = accountRepository;
}


public Account register(Account account){
if (account.getUsername() == null || account.getUsername().isBlank()) {
  throw new InvalidRegistrationException("blank username");
}
if (account.getPassword().length() < 4) {
  throw new InvalidRegistrationException("password less than 4");
}

Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
if (existing.isPresent()) {
  throw new DuplicateUsernameException("username exist");
}

return accountRepository.save(account);
}



public Account login(Account account){
  return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
  .orElseThrow(() -> 
new UnauthorizedUserException("account not found")
  );
  
}


}

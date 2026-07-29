package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidMessageException;
import com.example.exception.InvalidRegistrationException;
import com.example.exception.UnauthorizedUserException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
 public class SocialMediaController {

@Autowired
private AccountService accountService;
@Autowired
private MessageService messageService;

@PostMapping("/register")
public ResponseEntity<Account> register(@RequestBody Account account){
  Account saved = accountService.register(account);
  return ResponseEntity.ok().body(saved);
}


@PostMapping("/login")
public ResponseEntity<Account> login(@RequestBody Account account){
  return ResponseEntity.ok().body(accountService.login(account));
}


@PostMapping("/messages")
public ResponseEntity<Message> saveMessage(@RequestBody Message message){
  return ResponseEntity.ok().body(messageService.saveMessage(message));
}


@GetMapping("/messages")
public ResponseEntity<List<Message>> getMessages(){
  return ResponseEntity.ok().body(messageService.getMessages());
}




@ExceptionHandler(InvalidRegistrationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public String handleInvalid(InvalidRegistrationException e) {
    return e.getMessage();
}

@ExceptionHandler(DuplicateUsernameException.class)
@ResponseStatus(HttpStatus.CONFLICT)
public String handleDuplicate(DuplicateUsernameException e) {
    return e.getMessage();
}


@ExceptionHandler(UnauthorizedUserException.class)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public String handleUnauthorizedUser(UnauthorizedUserException e) {
    return e.getMessage();
}

@ExceptionHandler(InvalidMessageException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public String handleInvalidMessage(InvalidMessageException e) {
    return e.getMessage();
}


}

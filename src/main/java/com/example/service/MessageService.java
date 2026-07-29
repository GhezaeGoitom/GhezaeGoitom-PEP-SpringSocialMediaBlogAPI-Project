package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {


@Autowired  
private MessageRepository messageRepository;
@Autowired
private AccountRepository accountRepository;


public Message saveMessage(Message message){

  if(message.getMessageText().isBlank() || message.getMessageText() == null){
    throw new InvalidMessageException("empty message");
  }

  if (message.getMessageText().length() > 255) {
    throw new InvalidMessageException("message morethan 255 characters");
  }

accountRepository.findById(message.getPostedBy())
.orElseThrow(() -> new InvalidMessageException("user not found"));

return messageRepository.save(message);
}


public List<Message> getMessages(){
  return messageRepository.findAll();
}



}

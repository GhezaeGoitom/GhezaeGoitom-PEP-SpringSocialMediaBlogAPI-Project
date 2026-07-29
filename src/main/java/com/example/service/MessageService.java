package com.example.service;

import java.util.ArrayList;
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

public Message getMessageById(Integer messageId){
  return messageRepository.findById(messageId).orElse(null);
}

public Integer deleteById(Integer messageId){
 if (messageRepository.existsById(messageId)) {
  messageRepository.deleteById(messageId);
  return 1;
 } 
 return null;
}


public Integer updateMessageText(Message message, Integer messageId){

  if(message.getMessageText().isBlank() || message.getMessageText() == null){
    throw new InvalidMessageException("empty message");
  }

  if (message.getMessageText().length() > 255) {
    throw new InvalidMessageException("message morethan 255 characters");
  }

  Optional<Message> oldMessage = messageRepository.findById(messageId);
  if (!oldMessage.isPresent()) {
    throw new InvalidMessageException("message not found");
  }

  oldMessage.get().setMessageText(message.getMessageText());

  messageRepository.save(oldMessage.get());

  return 1;
}

public List<Message> getMessagesByAccountId(Integer accountId){
  return messageRepository.findAllByPostedBy(accountId).orElse(new ArrayList<Message>());
}



}

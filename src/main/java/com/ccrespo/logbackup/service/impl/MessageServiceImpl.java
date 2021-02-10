package com.ccrespo.logbackup.service.impl;

import com.ccrespo.logbackup.producer.MessageProducer;
import com.ccrespo.logbackup.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
  @Autowired
  MessageProducer logProducer;

  public void send(String message) {
    logProducer.send(message);
  }
}

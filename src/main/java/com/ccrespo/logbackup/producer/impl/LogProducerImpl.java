package com.ccrespo.logbackup.producer.impl;

import com.ccrespo.logbackup.producer.MessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogProducerImpl implements MessageProducer {

  Logger logger = LoggerFactory.getLogger(LogProducerImpl.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${logBackup.rabbitmq.exchange}")
  private String exchange;

  @Value("${logBackup.rabbitmq.queue}")
  private String queue;

  public void send(String message) {
    logger.info("Sending message: {}", message);
    rabbitTemplate.convertAndSend(exchange, queue, message);
    
  }
}
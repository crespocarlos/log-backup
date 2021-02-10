package com.ccrespo.logbackup.consumer;

import java.io.IOException;

import com.ccrespo.logbackup.service.LogWritterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {
  Logger logger = LoggerFactory.getLogger(LogConsumer.class);

  @Autowired
  LogWritterService logWritter;

  @RabbitListener(queues = "${logBackup.rabbitmq.queue}")
  public void receiveMessage(String message) throws IOException {

    logger.info("Received {}", message);
    logWritter.write(message);
  }

  @Bean
  public DeadLetterConsumer deadlLetterConsumer() {
    return new DeadLetterConsumer();
  }
}

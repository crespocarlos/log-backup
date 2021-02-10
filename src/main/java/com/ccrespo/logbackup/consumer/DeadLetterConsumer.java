package com.ccrespo.logbackup.consumer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class DeadLetterConsumer {
  private static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
  private static final int MAX_RETRIES_COUNT = 5;

  Logger logger = LoggerFactory.getLogger(DeadLetterConsumer.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${logBackup.rabbitmq.exchange}")
  private String exchange;

  @RabbitListener(queues = "${logBackup.rabbitmq.queue}")
  public void receiveMessage(Message message) {
    Integer retriesCount = (Integer) Optional
        .ofNullable(message.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT)).orElseGet(() -> 1);

    if (retriesCount > MAX_RETRIES_COUNT) {
      logger.info("More than {} attempts. Message discarded", retriesCount);
      return;
    }

    logger.info("Retrying message for the {} time", retriesCount);

    message.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCount);

    rabbitTemplate.send(exchange, message.getMessageProperties().getReceivedRoutingKey(), message);
  }
}

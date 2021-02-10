package com.ccrespo.logbackup.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${logBackup.rabbitmq.exchange}")
  private String exchange;

  @Value("${logBackup.rabbitmq.queue}")
  private String queue;

  @Value("${logBackup.rabbitmq.logExchange}")
  private String logExchange;

  @Value("${logBackup.rabbitmq.logQueue}")
  private String logQueue;

  @Value("${logBackup.rabbitmq.dlExchange}")
  private String deadLetterExchange;

  @Value("${logBackup.rabbitmq.dlQueue}")
  private String deadLetterQueue;

  @Bean
  Queue logQueue() {
    return QueueBuilder.durable(logQueue).withArgument("x-dead-letter-exchange", deadLetterQueue).build();
  }

  @Bean
  FanoutExchange logExchange() {
    return new FanoutExchange(logExchange);
  }

  @Bean
  Binding logBinding() {
    return BindingBuilder.bind(logQueue()).to(logExchange());
  }

  @Bean
  Queue deadLetterQueue() {
    return QueueBuilder.durable(deadLetterQueue).build();
  }

  @Bean
  FanoutExchange deadLetterExchange() {
    return new FanoutExchange(deadLetterExchange);
  }

  @Bean
  Binding deadLetterBinding() {
    return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
  }

  @Bean
  Queue messagesQueue() {
    return QueueBuilder.durable(queue).withArgument("x-dead-letter-exchange", deadLetterExchange).build();
  }

  @Bean
  DirectExchange messagesExchange() {
    return new DirectExchange(exchange);
  }

  @Bean
  Binding messagesBinding() {
    return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(queue);
  }
}

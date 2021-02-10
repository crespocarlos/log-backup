package com.ccrespo.logbackup.producer;

public interface MessageProducer {
  void send(String message);
}

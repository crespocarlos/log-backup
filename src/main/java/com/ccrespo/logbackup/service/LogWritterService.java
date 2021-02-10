package com.ccrespo.logbackup.service;

import java.io.IOException;

public interface LogWritterService {
  void write(String content) throws IOException;
}

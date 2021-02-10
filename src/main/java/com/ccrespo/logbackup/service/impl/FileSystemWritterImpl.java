package com.ccrespo.logbackup.service.impl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ccrespo.logbackup.service.LogWritterService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileSystemWritterImpl implements LogWritterService {
  private static final String LOG_FILE_NAME = "app-log-file.log";

  @Value("${logBackup.logFile.path}")
  private String path;

  public void write(String content) throws IOException {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    Path logFilePath = FileSystems.getDefault().getPath(path,
        String.format("%s-%s", df.format(new Date()), LOG_FILE_NAME));

    Files.writeString(logFilePath, (content + System.lineSeparator()), StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }
}
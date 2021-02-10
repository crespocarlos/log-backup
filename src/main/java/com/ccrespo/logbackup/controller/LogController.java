package com.ccrespo.logbackup.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ccrespo.logbackup.model.LogRequestModel;
import com.ccrespo.logbackup.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
@Validated
public class LogController {

  @Autowired
  MessageService messageService;

  @PostMapping(path = "/log")
  public void postLog(@RequestBody LogRequestModel log, HttpServletRequest request) {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    String message = String.format("%s - %s - %s.", df.format(new Date()), request.getRemoteAddr(), log.getContent());

    messageService.send(message);
  }
}

package com.warepulse.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotifyController {
    
    @Autowired
    private SimpMessagingTemplate messagingtemplate;

    public void notify(String message) {
        messagingtemplate.convertAndSend("/topic/notify", message);
    }
}

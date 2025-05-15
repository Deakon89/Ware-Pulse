package com.warepulse.controller;

import com.warepulse.model.Notification;
import com.warepulse.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotifyController {

    private final NotificationService svc;

    public NotifyController(NotificationService svc) { this.svc = svc; }


    @GetMapping
    public List<Notification> userNotifications() {
    return svc.findByCurrentUser();
}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        return svc.subscribe();
    }
}

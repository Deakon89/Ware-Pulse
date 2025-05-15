package com.warepulse.service;

import com.warepulse.model.User;
import com.warepulse.model.Notification;
import com.warepulse.repository.NotificationRepo;
import com.warepulse.repository.UserRepo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final NotificationRepo repo;
    private final UserService userService;
    private final UserRepo userRepo;
    
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificationService(NotificationRepo repo, UserService userService, UserRepo userRepo) {
        this.repo = repo;
        this.userService = userService;
        this.userRepo = userRepo;
    }

   
    public Notification publish(String message) {
        Notification toSave = new Notification();
        toSave.setMessage(message);
        toSave.setTimestamp(Instant.now());

         String username = currentUsername();
        User owner = userRepo.findByUsername(username).orElse(null);
        toSave.setOwner(owner);

        Notification saved = repo.save(toSave);
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(
                  SseEmitter.event()
                            .name("notification")
                            .data(saved)
                );
            } catch (IOException e) {
                
                emitters.remove(id);
            }
        });

        return saved;
    }

    
public List<Notification> findByCurrentUser() {
    String username = currentUsername();
    User user = userRepo.findByUsername(username)
                        .orElseThrow(); 
    return repo.findAllByOwnerOrderByTimestampDesc(user);
}


 
    public void delete(Long id) {
        repo.deleteById(id);
    }

   
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L); 
        String id = UUID.randomUUID().toString();
        emitters.put(id, emitter);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(()    -> emitters.remove(id));

        return emitter;
    }

    public String currentUsername() {
    return SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
}

public Notification saveAndSend(Notification n) {
    Notification saved = repo.save(n);

    emitters.forEach((id, emitter) -> {
        try {
            emitter.send(
              SseEmitter.event()
                        .name("notification")
                        .data(saved)
            );
        } catch (IOException e) {
            emitters.remove(id);
        }
    });

    return saved;
}
}



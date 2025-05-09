package com.warepulse.service;

import com.warepulse.model.Notification;
import com.warepulse.repository.NotificationRepo;

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

    // manteniamo tutti i client SSE connessi
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificationService(NotificationRepo repo) {
        this.repo = repo;
    }

    /** Chiamalo quando vuoi pubblicare una notifica: salva in DB e notifica tutti i client connessi */
    public Notification publish(String message) {
        Notification toSave = new Notification();
        toSave.setMessage(message);
        toSave.setTimestamp(Instant.now());
        Notification saved = repo.save(toSave);

        // invia l’evento a ciascun emitter aperto
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(
                  SseEmitter.event()
                            .name("notification")
                            .data(saved)
                );
            } catch (IOException e) {
                // cliente non risponde più: rimuovilo
                emitters.remove(id);
            }
        });

        return saved;
    }

    /** REST: restituisce tutte le notifiche salvate */
    public List<Notification> findAll() {
        return repo.findAllByOrderByTimestampDesc();
    }

    /** REST: elimina una specifica */
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /** SSE: endpoint per iscriversi allo stream */
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L); // 0 = nessun timeout
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
}



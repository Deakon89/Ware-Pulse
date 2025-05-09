package com.warepulse.repository;


import com.warepulse.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
    // per il findAll ordinato
    List<Notification> findAllByOrderByTimestampDesc();
    // List<Notification> findByUserUsername(String username);
}
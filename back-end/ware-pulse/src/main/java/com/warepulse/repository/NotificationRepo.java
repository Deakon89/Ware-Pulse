package com.warepulse.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.warepulse.model.Notification;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
 
    List<Notification> findAllByOrderByTimestampDesc();
    
}
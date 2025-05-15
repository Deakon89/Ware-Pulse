package com.warepulse.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.warepulse.model.Notification;
import com.warepulse.model.User;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
 
    List<Notification> findAllByOwnerOrderByTimestampDesc(User owner);
    
}
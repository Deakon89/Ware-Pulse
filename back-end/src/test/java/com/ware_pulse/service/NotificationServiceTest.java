// package com.ware_pulse.service;

// import com.warepulse.model.*;
// import com.warepulse.repository.*;
// import com.warepulse.service.NotificationService;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;




// @ExtendWith(MockitoExtension.class)
// class NotificationServiceTest {

//     @Mock
//     private NotificationRepo notificationRepo;

//     @Mock
//     private Authentication auth;

//     @InjectMocks
//     private NotificationService notificationService;

//     @Test
//     void testPublish() {
    
//     Authentication auth = mock(Authentication.class);
//     when(auth.getName()).thenReturn("testUser");

    
//     SecurityContext context = mock(SecurityContext.class);
//     when(context.getAuthentication()).thenReturn(auth);
//     SecurityContextHolder.setContext(context);

//     Notification n = new Notification();
//     n.setMessage("Test");

//     when(notificationRepo.save(any())).thenReturn(n);

//     Notification result = notificationService.publish("Test");

//     assertNotNull(result);
//     assertEquals("Test", result.getMessage());

    
//     SecurityContextHolder.clearContext();
// }
// }
package com.ware_pulse.service;

import com.warepulse.model.Notification;
import com.warepulse.model.User;
import com.warepulse.repository.NotificationRepo;
import com.warepulse.repository.UserRepo;
import com.warepulse.service.NotificationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepo notificationRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testPublish() {
        
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        
        User mockUser = new User();
        mockUser.setUsername("testUser");
        when(userRepo.findByUsername("testUser")).thenReturn(java.util.Optional.of(mockUser));

       
        Notification saved = new Notification();
        saved.setMessage("Test");
        when(notificationRepo.save(any())).thenReturn(saved);

        
        Notification result = notificationService.publish("Test");

        assertNotNull(result);
        assertEquals("Test", result.getMessage());

      
        SecurityContextHolder.clearContext();
    }
}

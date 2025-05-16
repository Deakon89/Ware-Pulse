package com.ware_pulse.service;

import com.warepulse.model.User;
import com.warepulse.repository.UserRepo;
import com.warepulse.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepo userRepo;
    private PasswordEncoder encoder;
    private UserService service;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        encoder = mock(PasswordEncoder.class);
        service = new UserService(userRepo, encoder);
    }

    @Test
    void testFindByUsername_existingUser() {
        User mockUser = new User();
        mockUser.setUsername("mario");
        when(userRepo.findByUsername("mario")).thenReturn(Optional.of(mockUser));

        User result = service.findByUsername("mario");

        assertNotNull(result);
        assertEquals("mario", result.getUsername());
        verify(userRepo, times(1)).findByUsername("mario");
    }

    @Test
    void testFindByUsername_notFound() {
        when(userRepo.findByUsername("non_esiste")).thenReturn(Optional.empty());

        User result = service.findByUsername("non_esiste");

        assertNull(result);
    }

    @Test
    void testRegister_shouldEncodePasswordAndSaveUser() {
        when(encoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.register("alice", "password123", "alice@example.com");

        assertNotNull(result);
        assertEquals("alice", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("alice@example.com", result.getEmail());
        assertEquals(List.of("USER"), result.getRoles());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture());

        User savedUser = captor.getValue();
        assertEquals("alice", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("alice@example.com", savedUser.getEmail());
    }
}

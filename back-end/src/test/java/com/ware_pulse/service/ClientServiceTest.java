package com.ware_pulse.service;

import com.warepulse.model.*;
import com.warepulse.repository.*;
import com.warepulse.service.ClientService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;



@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private ClientService clientService;

    @Test
    void testFindAll() {
        List<Client> clients = List.of(new Client(), new Client());
        when(clientRepo.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        Client c = new Client();
        when(clientRepo.save(c)).thenReturn(c);

        Client saved = clientService.save(c);

        assertEquals(c, saved);
    }

    @Test
    void testDelete() {
        clientService.delete(1L);
        verify(clientRepo).deleteById(1L);
    }
}

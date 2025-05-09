package com.warepulse.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.warepulse.model.Client;
import com.warepulse.model.User;
import com.warepulse.repository.ClientRepo;

import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private UserService userService;
    
    // Get all clients
    public List<Client> findAllClients() {
        Long ownerId = userService.currentUserId();
        return clientRepo.findByOwnerId(ownerId);
    }

    // Get client by id
    public Client findClientById(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    // Create a client
    public Client createClient(Client client) {
        User me = userService.currentUser().orElseThrow();
        client.setOwner(me);
        return clientRepo.save(client);
    }

    // Delete a client
    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }
}

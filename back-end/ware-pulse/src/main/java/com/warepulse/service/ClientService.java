package com.warepulse.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.warepulse.model.Client;
import com.warepulse.repository.ClientRepo;

import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepo clientRepo;
    
    // Get all clients
    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }

    // Get client by id
    public Client findClientById(Long id) {
        return clientRepo.findById(id).orElse(null);
    }

    // Create a client
    public Client createClient(Client client) {
        return clientRepo.save(client);
    }

    // Delete a client
    public void deleteClient(Long id) {
        clientRepo.deleteById(id);
    }
}

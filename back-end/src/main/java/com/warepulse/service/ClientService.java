
package com.warepulse.service;

import com.warepulse.model.Client;
import com.warepulse.model.User;
import com.warepulse.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    
    public Optional<Client> findById(Long id) {
        return clientRepo.findById(id);
    }

    public Client save(Client client) {
        return clientRepo.save(client);
    }

    public void delete(Long id) {
        clientRepo.deleteById(id);
    }


    public List<Client> findByOwner(User owner) {
        return clientRepo.findByOwner(owner);
    }
}


// src/main/java/com/warepulse/service/ClientService.java
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

    /** Recupera tutti i clienti (senza filtro). */
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    /** Recupera un cliente per ID. */
    public Optional<Client> findById(Long id) {
        return clientRepo.findById(id);
    }

    /** Crea o aggiorna un cliente. */
    public Client save(Client client) {
        return clientRepo.save(client);
    }

    /** Elimina un cliente per ID. */
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    /** Recupera solo i clienti appartenenti allo user indicato. */
    public List<Client> findByOwner(User owner) {
        return clientRepo.findByOwner(owner);
    }
}


package com.warepulse.model;

import jakarta.persistence.*;

@Entity
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   
    private Long id;

    private String nomeAttività;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    public Client() {}

    public Long getId() {    
        return id;
    }

    public String getNomeAttività() {
        return nomeAttività;
    }

    public void setNomeAttività(String nomeAttività) {
        this.nomeAttività = nomeAttività;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
}

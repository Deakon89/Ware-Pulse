package com.warepulse.model;

import jakarta.persistence.*;

@Entity
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   
    private Long id;

    private String nomeAttività;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;
    
    public Client() {}

    public Long getId() {    
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

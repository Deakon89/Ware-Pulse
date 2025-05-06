package com.warepulse.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "completed_orders")
public class CompletedOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long productId;
    private String productName;
    private int quantityOrdered;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    public CompletedOrder() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){    
        this.productId = productId;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public int getQuantityOrdered(){
        return quantityOrdered;
    }   

    public void setQuantityOrdered(int quantityOrdered){
        this.quantityOrdered = quantityOrdered;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Client getClient(){
        return client;
    }   

    public void setClient(Client client){    
        this.client = client;
    }

    public User getOwner(){
        return owner;
    }

    public void setOwner(User owner){
        this.owner = owner;
    }
}

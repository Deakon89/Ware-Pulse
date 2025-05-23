package com.warepulse.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @ManyToOne(fetch = FetchType.EAGER)  
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantityOrdered;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Order() {}
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

     public Product getProduct() {
         return product;
     }

     public void setProduct(Product product) {
         this.product = product;
     }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Date getDate() {
        return date;
    }   

     public void setDate(Date date) {
         this.date = date;
     }

     public OrderStatus getStatus() {
         return status;
     }

     public void setStatus(OrderStatus status) {
         this.status = status;
        }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}

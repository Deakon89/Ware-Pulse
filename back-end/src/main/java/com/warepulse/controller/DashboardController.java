// src/main/java/com/warepulse/controller/DashboardController.java
package com.warepulse.controller;

import com.warepulse.model.User;
import com.warepulse.model.Client;
import com.warepulse.model.Product;
import com.warepulse.service.ClientService;
import com.warepulse.service.ProductService;
import com.warepulse.service.UserService;
import com.warepulse.service.OrderService;
import com.warepulse.service.CompletedOrderService;
import com.warepulse.model.Order;
import com.warepulse.model.OrderStatus;
import com.warepulse.model.CompletedOrder;



import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ClientService clientService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CompletedOrderService completedOrderService;
    private final UserService userService;

    public DashboardController(ClientService clientService,
                               ProductService productService,
                               OrderService orderService,
                               CompletedOrderService completedOrderService,
                               UserService userService) {
        this.clientService = clientService;
        this.productService = productService;
        this.orderService = orderService;
        this.completedOrderService = completedOrderService;
        this.userService = userService;
    }

    @GetMapping("/clients")
    public List<Client> myClients(Authentication auth) {
         User username = userService.findByUsername(auth.getName());
        return clientService.findByOwner(username);
    }

    @PostMapping("/clients")
    public Client create(@RequestBody Client client, Authentication auth) {
    User user = userService.findByUsername(auth.getName());
    client.setOwner(user);
    return clientService.save(client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }

    @GetMapping("/products")
    public List<Product> myProducts(Authentication auth) {
        User username = userService.findByUsername(auth.getName());
        return productService.findByOwner(username);
    }

    

    @GetMapping("/orders")
    public List<Order> myOrders(Authentication auth) {
         User username = userService.findByUsername(auth.getName());
        return orderService.findByOwner(username);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, Authentication auth) {
    User owner = userService.findByUsername(auth.getName());
    order.setOwner(owner);
    order.setStatus(OrderStatus.NON_EVASO); 
    Order saved = orderService.save(order);
    return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed-orders")
    public List<CompletedOrder> myCompleted(Authentication auth) {
         User username = userService.findByUsername(auth.getName());
        return completedOrderService.findByOwner(username);
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<CompletedOrder> completeOrder(@PathVariable Long id) {
    Optional<Order> opt = orderService.findById(id);
    if (opt.isEmpty()) return ResponseEntity.notFound().build();

    Order order = opt.get();

    Product product = order.getProduct();
    int nuovaQuantita = product.getQuantity() - order.getQuantityOrdered();
    if (nuovaQuantita < 0) {
        return ResponseEntity.badRequest().body(null); 
    }
    product.setQuantity(nuovaQuantita);
    productService.save(product);

    CompletedOrder completed = new CompletedOrder();
    completed.setProductName(order.getProduct().getName());
    completed.setQuantityOrdered(order.getQuantityOrdered());
    completed.setDate(new Date());
    completed.setClient(order.getClient());
    completed.setOwner(order.getOwner());
    completed.setProductName(order.getProduct().getName());

    completedOrderService.save(completed);
    orderService.delete(id);

    return ResponseEntity.ok(completed);
    }

    @PostMapping("/products")
    public Product createProductDashboard(@RequestBody Product p, Authentication auth) {
    
    User me = userService.findByUsername(auth.getName());
    p.setOwner(me);
    return productService.save(p);
}

   @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
    productService.delete(id);
}  

 @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProductDashboard(@PathVariable Long id,
                                                         @RequestBody Product payload,
                                                         Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return productService.findById(id)
         
          .filter(p -> p.getOwner().equals(user))
          .map(existing -> {
            existing.setName(payload.getName());
            existing.setDescription(payload.getDescription());
            existing.setQuantity(payload.getQuantity());
            existing.setPrice(payload.getPrice());
            
            Product saved = productService.save(existing);
            return ResponseEntity.ok(saved);
          })
          .orElse(ResponseEntity.notFound().build());
            
    }

}

package com.warepulse.controller;

import com.warepulse.model.CompletedOrder;
import com.warepulse.model.Order;
import com.warepulse.model.Product;
import com.warepulse.service.ComplOrderService;
import com.warepulse.service.OrderService;
import com.warepulse.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashController {
//   private final OrderService orderSvc;
//   private final ProductService productSvc;

//   public DashController(OrderService orderSvc, ProductService productSvc){
//     this.orderSvc = orderSvc;
//     this.productSvc = productSvc;
//   }
@Autowired
private OrderService orderSvc;
@Autowired
private ProductService productSvc;
@Autowired 
private ComplOrderService completedService;

  @GetMapping("/orders")
  public List<Order> myOrders(Authentication auth){
    return orderSvc.findByOwnerUsername(auth.getName());
  }

  @GetMapping("/products")
  public List<Product> myProducts(Authentication auth){
    return productSvc.findMyProducts();
  }

  @GetMapping("/completed")
    public List<CompletedOrder> myCompleted() {
        return completedService.findAllCompletedOrders();
    }
}

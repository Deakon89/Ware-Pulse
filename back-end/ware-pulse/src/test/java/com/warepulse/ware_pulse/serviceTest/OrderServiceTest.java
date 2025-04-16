// package com.warepulse.ware_pulse.serviceTest;

// import com.warepulse.ware_pulse.WarePulseApplication;
// import com.warepulse.model.Client;
// import com.warepulse.model.CompletedOrder;
// import com.warepulse.model.Order;
// import com.warepulse.model.OrderStatus;
// import com.warepulse.model.Product;
// import com.warepulse.repository.ComplOrderRepo;
// import com.warepulse.repository.OrderRepo;
// import com.warepulse.service.OrderService;
// import com.warepulse.service.ProductService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.util.ReflectionTestUtils;

// import java.util.Date;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @SpringBootTest(classes = {WarePulseApplication.class})
// public class OrderServiceTest {

//     @Autowired
//     private OrderService orderService;
    
//     @Autowired
//     private OrderRepo orderRepo;
    
//     @Autowired
//     private ComplOrderRepo complOrderRepo;
    
//     @Autowired
//     private ProductService productService;

//     @Test
//     void testCompleteOrderSuccess() {
//         // Prepara l'ordine e le entità necessarie
//         Order order = new Order();
//         ReflectionTestUtils.setField(order, "id", 1L);
//         order.setStatus(OrderStatus.NON_EVASO);
        
//         Product product = new Product();
//         ReflectionTestUtils.setField(product, "id", 100L);
//         product.setName("Sample Product");
//         order.setProduct(product);
        
//         order.setQuantityOrdered(5);
//         order.setDate(new Date());
        
//         Client client = new Client();
//         ReflectionTestUtils.setField(client, "id", 2L);
//         client.setNomeAttività("Test Client");
//         order.setClient(client);

//         // Simula i comportamenti dei repository e del productService
//         // Stub: simuliamo che orderRepo.findById(1L) ritorni il nostro ordine
//         when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
//         // Per il metodo void usiamo doNothing()
//         doNothing().when(productService).updateProductQuantity(100L, 5);
//         // Stub per il salvataggio del CompletedOrder
//         when(complOrderRepo.save(any(CompletedOrder.class))).thenAnswer(invocation -> {
//             CompletedOrder co = invocation.getArgument(0);
//             ReflectionTestUtils.setField(co, "id", 10L);
//             return co;
//         });

//         // Esegui completeOrder e verifica i risultati
//         assertDoesNotThrow(() -> orderService.completeOrder(1L));
//         verify(productService, times(1)).updateProductQuantity(100L, 5);
//         verify(complOrderRepo, times(1)).save(any(CompletedOrder.class));
//         verify(orderRepo, times(1)).delete(order);
//     }
// }


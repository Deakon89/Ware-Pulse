// package com.warepulse.ware_pulse.ControllTest;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.warepulse.controller.OrderController;
// import com.warepulse.ware_pulse.WarePulseApplication;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.util.ReflectionTestUtils;
// import org.springframework.test.web.servlet.MockMvc;

// // Utilizza solo WebMvcTest per il controller
// @WebMvcTest(OrderController.class)
// @ContextConfiguration(classes = {WarePulseApplication.class})
// public class orderControlTest {

//     @Autowired
//     private MockMvc mockMvc;
    
//     @Autowired
//     private ObjectMapper objectMapper;
    
//     @MockBean
//     private com.warepulse.repository.OrderRepo orderRepo;
    
//     @MockBean
//     private com.warepulse.service.OrderService orderService;
    
//     @MockBean
//     private com.warepulse.service.ClientService clientService;
    
//     @MockBean
//     private com.warepulse.service.ProductService productService;

//     @Test
//     public void testCreateOrderSuccess() throws Exception {
//         // (implementa il test come nell'esempio precedentemente fornito)
//         String orderJson = "{\n" +
//             "  \"product\": { \"id\": 1 },\n" +
//             "  \"quantityOrdered\": 10,\n" +
//             "  \"client\": { \"id\": 2 }\n" +
//             "}";
        
//         mockMvc.perform(
//                 org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/orders")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(orderJson)
//             )
//             .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
//             // Aggiungi ulteriori asserzioni se necessario.
//     }
// }





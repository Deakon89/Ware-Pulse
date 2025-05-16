package com.ware_pulse.service;

import com.warepulse.model.Product;

import com.warepulse.repository.ProductRepo;
import com.warepulse.service.NotificationService;
import com.warepulse.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProductService productService;

    @Test
    void testFindAll() {
        List<Product> products = List.of(new Product(), new Product());
        when(productRepo.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        Product p = new Product();
        p.setId(1L);
        when(productRepo.findById(1L)).thenReturn(Optional.of(p));

        Optional<Product> result = productService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    
    
    @Test
    void testDelete() {
        productService.delete(1L);
        verify(productRepo).deleteById(1L);
    }
}

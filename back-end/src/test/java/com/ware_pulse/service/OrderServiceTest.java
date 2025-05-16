package com.ware_pulse.service;

import com.warepulse.model.*;
import com.warepulse.repository.*;
import com.warepulse.service.OrderService;

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
class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testFindAll() {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderRepo.findAll()).thenReturn(orders);

        List<Order> result = orderService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        Order o = new Order();
        o.setId(1L);
        when(orderRepo.findById(1L)).thenReturn(Optional.of(o));

        Optional<Order> result = orderService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testSave() {
        Order o = new Order();
        when(orderRepo.save(o)).thenReturn(o);

        Order saved = orderService.save(o);

        assertEquals(o, saved);
    }

    @Test
    void testDelete() {
        orderService.delete(1L);
        verify(orderRepo).deleteById(1L);
    }
}

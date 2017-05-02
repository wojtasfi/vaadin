package com.vaadin.service;

import com.vaadin.dao.OrderRepository;
import com.vaadin.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wojci on 5/2/2017.
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}

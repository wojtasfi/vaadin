package com.vaadin.service;

import com.vaadin.dao.OrderRepository;
import com.vaadin.entities.CustomerOrder;
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

    public void delete(CustomerOrder customerOrder) {
        orderRepository.delete(customerOrder);
    }

    public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

    public void save(CustomerOrder customerOrder) {
        orderRepository.save(customerOrder);
    }
}
